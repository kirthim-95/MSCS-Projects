import streamlit as st
import mysql.connector  # Assuming you're using MySQL for GCP
import pandas as pd
from mysql.connector import Error
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from dotenv import load_dotenv
import os
import io

load_dotenv()
 
# Access variables
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')

# Function to connect to GCP MySQL database (replace with your credentials)
def connect_to_gcp_mysql():
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

def get_table_names(connection):
    cursor = connection.cursor()
    cursor.execute("SHOW TABLES")
    table_names = [row[0] for row in cursor]
    return table_names

@st.cache_data
def run_query( query):
    try:
        connection = connect_to_gcp_mysql()
        cursor = connection.cursor()
        cursor.execute(query)
        result = cursor.fetchall()
        return result, cursor.description
    except Error as e:
        st.error(f"Error: {e}")
        return None, None

# Main app function
def app():
    st.subheader("Exploratory Data Analysis (EDA)")

    # Connect to GCP MySQL database
    connection = connect_to_gcp_mysql()
    #if connection is None:
    #    st.stop()  # Exit app if connection fails

    # Get table names from database
    table_names = get_table_names(connection)

    # Multi-select control for table selection
    selected_tables = st.multiselect("Select Tables", table_names)

    # Display selected table names
    if selected_tables:
        #st.write("Selected Tables:")
        #for table in selected_tables:
        #    st.write(f"- {table}")

        # Fetch the selected table data
        query = f"SELECT * FROM {selected_tables[0]}"
        result, description = run_query(query)

        # Convert the result to a pandas DataFrame
        df = pd.DataFrame(result, columns=[desc[0] for desc in description])

        # 1. See the whole dataset
        st.write("### 1. Full Dataset")
        st.dataframe(df.head())

        # 2. Get column names, data types info
        st.write("### 2. Column Information")
        # Create a DataFrame to summarize column information
        column_info = pd.DataFrame({
            'Column Name': df.columns,
            'Data Type': df.dtypes,
            'Non-Null Count': df.notnull().sum(),
            'Null Count': df.isnull().sum(),
            'Unique Values': [df[col].nunique() for col in df.columns]
        })
        st.dataframe(column_info) 

        # 3. Get the count and percentage of NA values
        st.write("### 3. Missing Values")
        missing_values = df.isnull().sum()
        missing_percentage = (missing_values / len(df)) * 100
        missing_data = pd.concat([missing_values, missing_percentage], axis=1, keys=['Missing Values', 'Missing %'])
        st.write(missing_data)
        # Dynamic explanation based on missing data
        if missing_percentage.max() > 30:
            st.write("""
            **This dataset has significant missing data.** 
            Over 30% of values are missing in some columns. This could impact the accuracy of any analysis. 
            Consider imputation or removal of these columns, depending on the specific data and analysis goals.
            """)
        elif missing_percentage.max() > 10:
            st.write("""
            **This dataset has moderate missing data.** 
            Between 10% and 30% of values are missing in some columns. 
            While this may not severely impact the analysis, it's worth investigating the reasons for missing data and considering imputation or removal.
            """)
        else:
            st.write("""
            **This dataset has minimal missing data.** 
            Less than 10% of values are missing in any column. 
            This is generally considered acceptable for most analyses.
            """)


        # 4. Get descriptive analysis 
        st.write("### 4. Descriptive Statistics")
        st.write(df.describe().T)

        # 5. Check imbalance or distribution of target variable (assuming a 'target' column)
        # if 'txId' in df.columns:
        #     st.write("### 5. Target Variable Distribution")
        #     fig, ax = plt.subplots()
        #     sns.countplot(x='txId', data=df, ax=ax)
        #     plt.title('Target Variable Distribution')
        #     st.pyplot(fig)

        # 6. See distribution of numerical columns
        st.write("### 5. Numerical Column Distributions")
        num_cols = df.select_dtypes(include=np.number).columns
        for col in num_cols:
            fig, ax = plt.subplots()
            hist = sns.histplot(df[col], kde=True, ax=ax)
            plt.title(f'Distribution of {col}')
            st.pyplot(fig)
            
            # Dynamic explanation based on the distribution
            kde_max = sns.kdeplot(df[col]).get_lines()[0].get_ydata().max()
            if kde_max > 10:
                st.write(f"""
                **{col} is highly skewed.** 
                This means that most of the values are concentrated on one side of the distribution. 
                This could be due to outliers or a non-normal distribution.
                """)
            elif kde_max > 5:
                st.write(f"""
                **{col} is moderately skewed.** 
                The values are somewhat concentrated on one side of the distribution. 
                This could impact the analysis, especially if the distribution is not normal.
                """)
            elif kde_max > 1:
                st.write(f"""
                **{col} is slightly skewed.** 
                The values are slightly concentrated on one side of the distribution. 
                This may not significantly impact the analysis.
                """)
            else:
                st.write(f"""
                **{col} is approximately normally distributed.** 
                This means that the values are evenly distributed around the mean. 
                This is a good assumption for many statistical tests.
                """)

            # Additional conditions for specific patterns
            # if sns.histplot(df[col]).get_patches()[0].get_height() > 0.8 * len(df):
            #     st.write(f"""
            #     **{col} is heavily concentrated around a single value.** 
            #     This could indicate a limited range of values or potential data quality issues.
            #     """)
            # elif len(df[col].unique()) < 5:
            #     st.write(f"""
            #     **{col} has a limited number of unique values.** 
            #     This could suggest that the variable is categorical or has low variability.
            #     """)
            # patches = hist.patches  # Get the patches from the histogram

            # if patches:  # Ensure there are patches to check
            #     max_height = max(patch.get_height() for patch in patches)  # Get the maximum height of the bars
            #     if max_height > 0.8 * len(df):
            #         st.write(f"""
            #         **{col} is heavily concentrated around a single value.** 
            #         This could indicate a limited range of values or potential data quality issues.
            #         """)
            # elif len(df[col].unique()) < 5:
            #     st.write(f"""
            #     **{col} has a limited number of unique values.** 
            #     This could suggest that the variable is categorical or has low variability.
            #     """)
            

        # 7. See count plot of categorical columns
        st.write("### 6. Categorical Column Counts")
        cat_cols = df.select_dtypes(include='object').columns
        for col in cat_cols:
            fig, ax = plt.subplots()
            sns.countplot(x=col, data=df, ax=ax)
            plt.title(f'Countplot of {col}')
            st.pyplot(fig)

            # Dynamic explanation based on the count plot
            value_counts = df[col].value_counts()
            max_count = value_counts.max()
            min_count = value_counts.min()

            if max_count / len(df) > 0.8:
                st.write(f"""
                **{col} is heavily dominated by a single category.** 
                Over 80% of the observations belong to one category. 
                This could indicate a skewed distribution or potential data quality issues. 
                
                **Next Steps:**
                - Investigate the reason for the dominance of one category.
                - Consider combining less frequent categories into an 'Other' category.
                - Explore techniques like oversampling or undersampling to balance the class distribution.
                """)
            elif max_count / len(df) > 0.5:
                st.write(f"""
                **{col} is dominated by a few categories.** 
                More than 50% of the observations are concentrated in a few categories. 
                This could impact the analysis, especially if the categories are imbalanced. 
                
                **Next Steps:**
                - Consider combining less frequent categories into an 'Other' category.
                - Explore techniques like oversampling or undersampling to balance the class distribution.
                """)
            elif min_count == 0:
                st.write(f"""
                **{col} has a category with no observations.** 
                This could indicate data quality issues or a specific characteristic of the data. 
                
                **Next Steps:**
                - Investigate the reason for the missing category.
                - Consider removing the category or treating it as a missing value.
                """)
            else:
                st.write(f"""
                **{col} has a relatively balanced distribution of categories.** 
                This is generally a good starting point for analysis. 
                
                **Next Steps:**
                - Explore the relationship between this categorical variable and other variables in the dataset.
                - Consider using appropriate statistical tests or machine learning techniques to analyze categorical data.
                """)


        # 8. Get outlier analysis with box plots
        st.write("### 7. Outlier Analysis")
        for col in num_cols:
            fig, ax = plt.subplots()
            sns.boxplot(x=df[col], ax=ax)
            plt.title(f'Boxplot of {col}')
            st.pyplot(fig)

            # Dynamic explanation based on the box plot
            q1 = df[col].quantile(0.25)
            q3 = df[col].quantile(0.75)
            iqr = q3 - q1
            lower_bound = q1 - 1.5 * iqr
            upper_bound = q3 + 1.5 * iqr

            if df[col].min() < lower_bound or df[col].max() > upper_bound:
                st.write(f"""
                **{col} has potential outliers.** 
                The box plot shows data points that fall outside the expected range. 
                These outliers could be due to measurement errors, data entry errors, or genuine extreme values.

                **Next Steps:**
                - Investigate the cause of the outliers.
                - Consider removing outliers if they are clearly erroneous or if they significantly impact the analysis.
                - Use robust statistical methods that are less sensitive to outliers.
                """)
            else:
                st.write(f"""
                **{col} has no significant outliers.** 
                The box plot shows a relatively normal distribution of values. 

                **Next Steps:**
                - Continue with your analysis, but keep an eye out for potential outliers that might emerge in future data.
                """)

        # 9. Obtain info of target value variance with categorical columns
        if 'target' in df.columns:
            st.write("### 8. Target Value Variance by Categorical Columns")
            for col in cat_cols:
                fig, ax = plt.subplots()
                sns.boxplot(x=col, y='target', data=df, ax=ax)
                plt.title(f'Target Value Variance by {col}')
                st.pyplot(fig)

                # Dynamic explanation based on the box plot
                if len(df[col].unique()) > 10:
                    st.write(f"""
                    **{col} has many categories.** 
                    The box plot shows the distribution of the target variable for each category. 
                    It can be challenging to interpret the plot with many categories.

                    **Next Steps:**
                    - Consider grouping similar categories together or reducing the number of categories.
                    - Use statistical tests to compare the means of the target variable between different categories.
                    """)
                elif len(df[col].unique()) > 5:
                    st.write(f"""
                    **{col} has several categories.** 
                    The box plot shows the distribution of the target variable for each category. 
                    Look for differences in the median, quartiles, and the presence of outliers.

                    **Next Steps:**
                    - Use statistical tests to compare the means of the target variable between different categories.
                    - Consider using one-hot encoding or other techniques to represent categorical variables in machine learning models.
                    """)
                else:
                    st.write(f"""
                    **{col} has a few categories.** 
                    The box plot shows the distribution of the target variable for each category. 
                    Look for differences in the median, quartiles, and the presence of outliers.

                    **Next Steps:**
                    - Use statistical tests to compare the means of the target variable between different categories.
                    - Consider using one-hot encoding or other techniques to represent categorical variables in machine learning models.
                    """)

    # Close database connection (if successfully established)
    if connection is not None:
        connection.close()


# Call the app function to run the dashboard logic
if __name__ == "__main__":
    app()