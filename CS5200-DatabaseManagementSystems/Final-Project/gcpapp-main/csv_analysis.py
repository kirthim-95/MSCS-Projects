import streamlit as st
import pandas as pd
import mysql.connector
import matplotlib.pyplot as plt
import seaborn as sns
from dotenv import load_dotenv
# from sqlalchemy import create_engine, inspect
import os


load_dotenv()

# Load environment variables for MySQL connection
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')

# Function to connect to MySQL database
def connect_to_mysql():
    try:
        connection = mysql.connector.connect(
            host=MYSQL_HOST,
            user=MYSQL_USER,
            password=MYSQL_PASSWORD,
            database=MYSQL_DATABASE,
            auth_plugin='mysql_native_password'
        )
        return connection
    except Exception as e:
        st.error(f"Connection error: {e}")
        return None

# Function to get table names
def get_table_names(connection):
    cursor = connection.cursor()
    cursor.execute("SHOW TABLES")
    table_names = [row[0] for row in cursor]
    return table_names

# Function to get column names for a specific table
def get_column_names(connection, table_name):
    cursor = connection.cursor()
    cursor.execute(f"SHOW COLUMNS FROM {table_name}")
    column_names = [row[0] for row in cursor]
    return column_names

def reset_analyze_pressed():
    st.session_state.analyze_pressed = False

def get_column_data_type(table_name, column_name):
    connection = connect_to_mysql()
    cursor = connection.cursor()
    
    # Query to get the column type from the information schema
    query = f"""
    SELECT DATA_TYPE 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = %s AND COLUMN_NAME = %s
    """
    
    cursor.execute(query, (table_name, column_name))
    result = cursor.fetchone()
    cursor.close()
    
    if result:
        # column_type = result[0]
        # print(f"Column Type for {table_name}.{column_name}: {column_type}")
        # print(f"############################")
        column_type = result[0].decode('utf-8')
    
        # Map MySQL data types to user-friendly strings
        if column_type in ['int', 'bigint', 'smallint', 'tinyint']:
            return 'int'
        elif column_type in ['float', 'double', 'decimal']:
            return 'float'
        elif column_type in ['date', 'datetime', 'timestamp']:
            return 'date'
        elif column_type in ['varchar', 'text', 'char']:
            return 'categorical'
        # Add other type mappings as needed
        else:
            return 'unknown'
    else:
        return None  # Column not found

# @st.cache_data
# def load_mysql_data(selected_table):
#     """Load data from MySQL database."""
#     connection = connect_to_mysql()  # Establish the connection here
#     if connection is not None:
#         mysql_data = pd.read_sql(f"SELECT * FROM {selected_table}", connection)
#         connection.close()  # Close the connection after loading data
#         return mysql_data
#     else:
#         return None
@st.cache_data
def load_mysql_data(selected_table,selected_table_column, start_date=None, end_date=None):
    """Load data from MySQL database with optional date filtering."""
    connection = connect_to_mysql()  # Establish the connection here
    if connection is not None:
        if start_date and end_date:
            query = f"SELECT * FROM {selected_table} WHERE {selected_table_column} BETWEEN %s AND %s"
            mysql_data = pd.read_sql(query, connection, params=(start_date, end_date))
        else:
            mysql_data = pd.read_sql(f"SELECT * FROM {selected_table}", connection)
        
        connection.close()  # Close the connection after loading data
        return mysql_data
    else:
        return None

@st.cache_data
def preprocess_data(mysql_data, selected_table_column, csv_data, selected_csv_column):
    """Preprocess the data by converting date columns and merging."""
    # Convert the MySQL datetime column to date
    mysql_data[selected_table_column] = pd.to_datetime(mysql_data[selected_table_column]).dt.date
    
    # If the CSV column is also in date format, convert it to datetime.date
    csv_data[selected_csv_column] = pd.to_datetime(csv_data[selected_csv_column]).dt.date

    # Merge the data
    merged_data = pd.merge(csv_data, mysql_data, 
                            left_on=selected_csv_column, right_on=selected_table_column)
    return merged_data


def get_date_range(table_name, column_name):
    connection = connect_to_mysql()
    query = f"SELECT MIN({column_name}), MAX({column_name}) FROM {table_name}"
    cursor = connection.cursor()
    cursor.execute(query)
    min_date, max_date = cursor.fetchone()
    cursor.close()
    return min_date, max_date

def get_numeric_range( table_name, column_name):
    connection = connect_to_mysql()
    query = f"SELECT MIN({column_name}), MAX({column_name}) FROM {table_name}"
    cursor = connection.cursor()
    cursor.execute(query)
    min_value, max_value = cursor.fetchone()
    cursor.close()
    return min_value, max_value

def get_unique_categories( table_name, column_name):
    connection = connect_to_mysql()
    query = f"SELECT DISTINCT {column_name} FROM {table_name}"
    cursor = connection.cursor()
    cursor.execute(query)
    categories = [row[0] for row in cursor.fetchall()]
    cursor.close()
    return categories

# Main app function
def app():
    st.title("Additional Data Correlation Analysis")

    # Connect to MySQL
    connection = connect_to_mysql()
    
    if connection is not None:
        # Get table names
        table_names = get_table_names(connection)

        # Column selection in col1
        col1, col2 = st.columns(2)

        with col1:
            st.subheader("Select a Table")
            selected_table = st.selectbox("Choose a table", table_names, on_change=reset_analyze_pressed)

            # Get columns for the selected table
            table_columns = get_column_names(connection, selected_table)
            selected_table_column = st.selectbox("Select a column from the table", table_columns, on_change=reset_analyze_pressed)

            # Get the data type of the selected column
            column_data_type = get_column_data_type(selected_table, selected_table_column)
            st.write(column_data_type)

            ss = None
            ee = None
            # Display a smart filter based on the data type
            if column_data_type == 'date':
                min_date, max_date = get_date_range(selected_table, selected_table_column)
                start_date, end_date = st.slider("Select date range", min_value=min_date, max_value=max_date, value=(min_date, max_date),on_change=reset_analyze_pressed)
                ss = start_date
                ee = end_date
            elif column_data_type in ['int', 'float']:
                min_value, max_value = get_numeric_range(selected_table, selected_table_column)
                start_value, end_value = st.slider("Select numeric range", min_value=min_value, max_value=max_value, value=(min_value, max_value),on_change=reset_analyze_pressed)
                ss = start_value
                ee = end_value
            
            # elif column_data_type == 'categorical':
            #     categories = get_unique_categories(selected_table, selected_table_column)
            #     selected_categories = st.multiselect("Select categories", categories)

            # Upload CSV file
            uploaded_file = st.file_uploader("Upload a CSV file", type="csv")

        with col2:
            if selected_table:
                # st.subheader("Table Data Preview:")
                # st.dataframe(csv_data.head())
                # st.write(f"Columns in {selected_table}: {table_columns}")

                # If a CSV is uploaded, show its columns
                if uploaded_file is not None:
                    csv_data = pd.read_csv(uploaded_file)
                    st.write("CSV Data Preview:")
                    st.dataframe(csv_data.head())

                    # Show columns from the CSV
                    csv_columns = csv_data.columns.tolist()
                    selected_csv_column = st.selectbox("Select a column from the CSV", csv_columns,on_change=reset_analyze_pressed)

        if "analyze_pressed" not in st.session_state:
            st.session_state.analyze_pressed = False

        if selected_table and uploaded_file is not None:
            # Join the data based on selected columns
            if st.button("Analyze"):
                # Perform the join operation
                # merged_data = pd.merge(csv_data, pd.read_sql(f"SELECT * FROM {selected_table}", connection), 
                                        # left_on=selected_csv_column, right_on=selected_table_column)
                # Perform the join operation
                # mysql_data = pd.read_sql(f"SELECT * FROM {selected_table}", connection)
                # Load MySQL data with caching
                mysql_data = load_mysql_data(selected_table,selected_table_column, ss, ee)
                
                # # Convert the MySQL datetime column to date
                # mysql_data[selected_table_column] = pd.to_datetime(mysql_data[selected_table_column]).dt.date
                
                # # If the CSV column is also in date format, convert it to datetime.date
                # csv_data[selected_csv_column] = pd.to_datetime(csv_data[selected_csv_column]).dt.date

                # # Merge the data
                # merged_data = pd.merge(csv_data, mysql_data, 
                #                         left_on=selected_csv_column, right_on=selected_table_column)
                # Preprocess data with caching
                if mysql_data is not None:
                    

                    # Set the analyze button pressed state to True
                    st.session_state.analyze_pressed = True
                    merged_data = preprocess_data(mysql_data, selected_table_column, csv_data, selected_csv_column)
                    # Preprocess data with caching
                    st.session_state.merged_data = merged_data

        if st.session_state.analyze_pressed:
            st.write("Merged Data Preview:")
            st.dataframe(st.session_state.merged_data)

            # Generate charts
            st.subheader("Correlation Analysis")

            # Chart 1: Scatter plot
            col = st.columns((2, 8), gap='medium')

            with col[0]:
                x_axis_column1 = st.selectbox("Select x-axis column", st.session_state.merged_data.columns)
                y_axis_column1 = st.selectbox("Select y-axis column", st.session_state.merged_data.columns)
            with col[1]:
                plt.figure(figsize=(10, 5))
                sns.scatterplot(data=st.session_state.merged_data, x=x_axis_column1, y=y_axis_column1)
                plt.title("Scatter Plot")
                st.pyplot(plt)

            # Explanation of the scatter plot
            # if st.session_state.merged_data[x_axis_column1].dtype in ['int64', 'float64'] and st.session_state.merged_data[y_axis_column1].dtype in ['int64', 'float64']:
            st.write(f"The scatter plot shows the relationship between the **{x_axis_column1}** and **{y_axis_column1}**.")
            st.write("Each point represents a pair of values from these two columns.")

            # Explanation of point density
            st.write("If you notice a higher density of points on one side of the plot, it may indicate that most of the data points fall within a specific range for one of the variables.")
            st.write(f"For example, if there are many points clustered towards the left side of the x-axis, it suggests that the values of **{x_axis_column1}** are generally low.")

            # Correlation interpretation
            st.write("If the points tend to rise together from left to right, it indicates a positive correlation, meaning that as one variable increases, the other tends to increase as well.")
            st.write("Conversely, if the points slope downward from left to right, this indicates a negative correlation, suggesting that as one variable increases, the other tends to decrease.")
            st.write("If the points are scattered without any discernible pattern, it may suggest that there is little to no correlation between the two variables.")

            # Outlier discussion
            st.write("Look for any points that stand out from the main cluster; these are known as outliers.")
            st.write("Outliers can indicate unusual observations or errors in the data, and they may require further investigation.")

            # Trend analysis
            st.write("If the scatter plot is being used to analyze trends over time or across categories, consider how the distribution of points changes.")
            st.write("For instance, you may observe that certain categories show distinct patterns, which could inform decision-making or further analysis.")

            # else:
            #     st.write(f"The scatter plot shows the relationship between the **{x_axis_column1}** and **{y_axis_column1}**.")
            #     st.write("Since one or both of these columns are not numerical, the interpretation may vary.")
            #     st.write("If they are categorical, this plot can help identify how different categories relate to each other.")
            #     st.write("You might want to consider other analysis techniques for categorical data.")
            
            
            # Chart 2: Correlation Heatmap
            plt.figure(figsize=(10, 5))

            # Select only numeric columns for correlation analysis
            numeric_columns = st.session_state.merged_data.select_dtypes(include=['number']).columns
            if len(numeric_columns) > 0:
                correlation_matrix = st.session_state.merged_data[numeric_columns].corr()
                
                # Generate the heatmap
                sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm')
                plt.title("Correlation Heatmap")
                st.pyplot(plt)
            else:
                st.write("No numeric columns available for correlation analysis.")

            # # Chart 3: Box plot
            # plt.figure(figsize=(10, 5))
            # sns.boxplot(data=st.session_state.merged_data, x=selected_csv_column, y=selected_table_column)
            # plt.title("Box Plot")
            # st.pyplot(plt)

            # Chart 4: Histogram
            col = st.columns((2, 8), gap='medium')

            with col[0]:
                # Get columns from table and CSV
                table_columns = get_column_names(connection, selected_table)
                csv_columns = csv_data.columns.tolist()

                # Add dropdowns for x and y axes
                x_axis_column = st.selectbox("Select x-axis column from table", table_columns)
                y_axis_column = st.selectbox("Select y-axis column from CSV", csv_columns)
            with col[1]:
                plt.figure(figsize=(10, 5))

                # Plot histogram
                sns.histplot(st.session_state.merged_data[y_axis_column], bins=30, kde=True, label=y_axis_column)
                plt.xlabel(x_axis_column)
                plt.ylabel("Frequency")
                plt.title("Histogram of CSV Column")
                plt.legend()
                st.pyplot(plt)

            # # Chart 5: Line plot
            # plt.figure(figsize=(10, 5))
            # sns.lineplot(data=st.session_state.merged_data, x=selected_csv_column, y=selected_table_column)
            # plt.title("Line Plot")
            # st.pyplot(plt)           

        # Close the database connection
        connection.close()

# Run the app
if __name__ == "__main__":
    app()