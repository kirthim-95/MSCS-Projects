import streamlit as st
import pandas as pd
import mysql.connector
from dotenv import load_dotenv
import os

load_dotenv()

# Access variables
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')

# Function to connect to MySQL database
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

# @st.cache_data
# def load_data_from_gcp(table, percentage):
#     connection = connect_to_gcp_mysql()
#     if connection is None:
#         return None

#     cursor = connection.cursor()
#     stored_proc_call = f"SELECT * FROM {table}"
#     cursor.execute(stored_proc_call)
#     results = cursor.fetchall()
#     column_names = [desc[0] for desc in cursor.description]
#     df = pd.DataFrame(results, columns=column_names)
#     connection.close()
#     return df

def load_data_from_gcp(table, percentage):
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None

    
    cursor = connection.cursor()
    # print(cursor)
    stored_proc_call = "CALL GetDataByPercentage(%s, %s)"
    for result in cursor.execute(stored_proc_call, (table, percentage), multi=True):
        if result.with_rows:
            results = result.fetchall()
            column_names = [desc[0] for desc in result.description]
            transactions_df = pd.DataFrame(results, columns=column_names)
            return (transactions_df)
   
    connection.close()
    
    return (transactions_df)

# @st.cache_data # Apply caching to data loading function
def load_filtered_data_from_gcp(table, filter_condition):
    # Connect to your GCP MySQL database
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None
    
    # Construct the query with the specified filter condition
    query = f"SELECT * FROM {table} WHERE {filter_condition}"
    print(f"############################### SELECT * FROM {table}")
    
    # Execute the query and load the data into a DataFrame
    df = pd.read_sql(query, connection)
    connection.close()
    
    return df

# @st.cache_data # Apply caching to data loading function
def load_filtered_data_from_gcpLimit(table, filter_condition):
    # Connect to your GCP MySQL database
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None
    
    # Construct the query with the specified filter condition
    query = f"SELECT * FROM {table} WHERE {filter_condition} LIMIT 50000"
    print(f"############################### SELECT * FROM {table} LIMIT")
    
    # Execute the query and load the data into a DataFrame
    df = pd.read_sql(query, connection)
    connection.close()
    
    return df

def app():
    st.title("Illicit Transactions Overview")

    # Load data
    percentage_options = 2
    classes_df = load_data_from_gcp('classes',100)
    wallet_classes_df = load_data_from_gcp('wallet_classes',100)
    transactions_df = load_data_from_gcp('Transactions', percentage_options)
    # transactions_df = load_data_from_gcp('Transactions', 100)
    # classes_df = load_data_from_gcp('classes', 100)
    if transactions_df is not None and not transactions_df.empty:
        # Get the list of txids from transactions_df
        txid_list = transactions_df['txId'].tolist()

        txs_edgelist_df = load_filtered_data_from_gcp('txs_edgelist', f'txId1 IN ({",".join(map(str, txid_list))}) OR txId2 IN ({",".join(map(str, txid_list))})')
        print("############################### loaded txs_edgelist_df")
        print(len(txs_edgelist_df))

        TxAddr_edgelist_df = load_filtered_data_from_gcpLimit('TxAddr_edgelist', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded TxAddr_edgelist_df")
        print(len(TxAddr_edgelist_df))

        Addr_Vol_Combined_df = load_filtered_data_from_gcpLimit('Addr_Vol_Combined', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded Addr_Vol_Combined_df")
        print(len(Addr_Vol_Combined_df))

        AddrTx_edgelist_df = load_filtered_data_from_gcp('AddrTx_edgelist', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded AddrTx_edgelist_df")
        print(len(AddrTx_edgelist_df))

    # txs_edgelist_df = load_data_from_gcp('txs_edgelist', 100)
    # TxAddr_edgelist_df = load_data_from_gcp('TxAddr_edgelist', 100)
    # AddrTx_edgelist_df = load_data_from_gcp('AddrTx_edgelist', 100)
    # Addr_Vol_Combined_df = load_data_from_gcp('Addr_Vol_Combined', 100)
    # print(Addr_Vol_Combined_df.head())
    # print("####### Addr_Vol_Combined_df")
    Transaction_details_df = load_data_from_gcp('Transaction_details', 100) 
    print(Transaction_details_df)
    print("####### Transaction_details_df")
    
    # Filter for illicit transactions
    illicit_classes = classes_df[classes_df['name'] == 'illicit']
    illicit_txids = transactions_df[transactions_df['class'].isin(illicit_classes['class'])]

    # # Add a session state to keep track of the previous selected txId
    # if 'previous_txid' not in st.session_state:
    #     st.session_state.previous_txid = None


    col1, col2 = st.columns(2)

    with col1:
        # Dropdown for selecting a transaction ID
        st.subheader("Select an Illicit Transaction ID")
        selected_txid = st.selectbox("Transaction ID", illicit_txids['txId'].tolist())
    with col2:
        # Display selected illicit transaction details
        st.write("Selected Transaction Details:")
        selected_transaction = illicit_txids[illicit_txids['txId'] == selected_txid]
        st.dataframe(selected_transaction)

    col1, col2, col3 = st.columns(3)

    with col1:
        related_txids = txs_edgelist_df[(txs_edgelist_df['txId1'] == selected_txid) | (txs_edgelist_df['txId2'] == selected_txid)]
        st.subheader("Related Transaction IDs")
        st.dataframe(related_txids)
    with col2:
        # Get related addresses from TxAddr_edgelist_df
        related_addresses = TxAddr_edgelist_df[TxAddr_edgelist_df['txId'] == selected_txid]
        st.subheader("Related Receiver Addresses")
        st.dataframe(related_addresses)
    with col3:
        # Get related addresses from AddrTx_edgelist_df
        related_input_addresses = AddrTx_edgelist_df[AddrTx_edgelist_df['txId'] == selected_txid]
        st.subheader("Related Sender Addresses")
        st.dataframe(related_input_addresses)

    # Show relationship with Addr_Vol_Combined_df
    st.subheader("Relationship with Address Volume Combined Data")
    addr_vol_data = Addr_Vol_Combined_df[Addr_Vol_Combined_df['txId'] == selected_txid]

    if not addr_vol_data.empty:
        st.dataframe(addr_vol_data)
    else:
        st.write("No related address volume data found for the selected transaction ID.")

    # User input for notes
    st.subheader("Add Your Notes")
    # existing_record = Transaction_details_df[Transaction_details_df['txId'] == selected_txid]
    # if not existing_record.empty:
    #     description = existing_record.iloc[0]['description']  # Pre-fill with existing description
    # else:
    #     description = ""
    # if st.session_state.previous_txid != selected_txid:
    #     # If it has changed, reset the description
    #     st.session_state.previous_txid = selected_txid
    #     description = ""  # Reset the description
    # else:
        # Check for existing description
    existing_record = Transaction_details_df[Transaction_details_df['txId'] == int(selected_txid)]
    print(f"----->existing_record {existing_record}")
    print(f"----->selected_txid {selected_txid}")
    if not existing_record.empty:
        description = existing_record.iloc[0]['description']  # Pre-fill with existing description
    else:
        description = ""

    
    # Check for existing description
    # existing_record = Transaction_details_df[Transaction_details_df['txId'] == selected_txid]
    # if not existing_record.empty:
    #     description = existing_record.iloc[0]['description']  # Pre-fill with existing description
    # else:
    #     description = ""

    # Text area for description
    new_description = st.text_area("Description", value=description)

    if st.button("Submit"):
        # Check if there is an existing record
        if not existing_record.empty:
            # Update the existing record
            connection = connect_to_gcp_mysql()
            cursor = connection.cursor()
            update_query = """
                UPDATE Transaction_details
                SET description = %s
                WHERE txId = %s
            """
            cursor.execute(update_query, (new_description, selected_txid))
            connection.commit()
            cursor.close()
            connection.close()
            st.success("Note updated successfully!")
        else:
            # Insert new record
            connection = connect_to_gcp_mysql()
            cursor = connection.cursor()
            insert_query = """
                INSERT INTO Transaction_details (description, txId)
                VALUES (%s, %s)
            """
            cursor.execute(insert_query, (new_description, selected_txid))
            connection.commit()
            cursor.close()
            connection.close()
            st.success("Note added successfully!")

    # Delete button for removing the description
    if st.button("Delete Description"):
        if not existing_record.empty:
            connection = connect_to_gcp_mysql()
            cursor = connection.cursor()
            delete_query = """
                DELETE FROM Transaction_details
                WHERE txId = %s
            """
            cursor.execute(delete_query, (selected_txid,))
            connection.commit()
            cursor.close()
            connection.close()
            st.success("Description deleted successfully!")
            description = ""
        else:
            st.warning("No description found to delete.")

if __name__ == "__main__":
    app()