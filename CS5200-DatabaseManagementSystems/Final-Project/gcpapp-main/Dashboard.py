# Import libraries
import streamlit as st
import pandas as pd
import altair as alt
import plotly.express as px
import networkx as nx
import matplotlib.pyplot as plt
import mysql.connector
from dotenv import load_dotenv
import os

#######################
# Page configuration
st.set_page_config(
    page_title="Bitcoin Transactions Dashboard",
    page_icon="₿",
    layout="wide",
    initial_sidebar_state="expanded"
)
alt.themes.enable("dark")

load_dotenv()
 
# Access variables
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')
#######################
# CSS styling
st.markdown("""
<style>

[data-testid="block-container"] {
    padding-left: 2rem;
    padding-right: 2rem;
    padding-top: 1rem;
    padding-bottom: 0rem;
    margin-bottom: -7rem;
}

[data-testid="stVerticalBlock"] {
    padding-left: 0rem;
    padding-right: 0rem;
}

[data-testid="stMetric"] {
    background-color: #393939;
    text-align: center;
    padding: 15px 0;
}

[data-testid="stMetricLabel"] {
  display: flex;
  justify-content: center;
  align-items: center;
}

[data-testid="stMetricDeltaIcon-Up"] {
    position: relative;
    left: 38%;
    -webkit-transform: translateX(-50%);
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
}

[data-testid="stMetricDeltaIcon-Down"] {
    position: relative;
    left: 38%;
    -webkit-transform: translateX(-50%);
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
}

@import url('https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap');

/* DASHBOARD PADDING */
div.block-container.css-z5fcl4.egzxvld4 {
    width: 100%;
    min-width: auto;
    max-width: initial;
    padding-left: 5rem;
    padding-right: 5rem;
    padding-top: 15px;
    padding-bottom: 40px;
}

/* GLOBAL FONT CHANGE */
html, body, [class*="css"] {
    font-family: 'Space Grotesk'; 
}

.st-ae {
    font-family: 'Space Grotesk';
}

/* CONTAINER CSS */
[data-testid="stVerticalBlock"] > [style*="flex-direction: column;"] > [data-testid="stVerticalBlock"] {
    border: 1px groove #52546a;
    border-radius: 10px;
    padding-left: 25px;
    padding-top: 10px;
    padding-bottom: 10px;
    box-shadow: -6px 8px 20px 1px #00000052;
}

/* CUSTOM MARKDOWN CLASSES */
.dashboard_title {
    font-size: 20px; 
    font-family: 'Space Grotesk';
    font-weight: 700;
    line-height: 1.2;
    text-align: left;
    padding-bottom: 35px;
}


.price_details {
    font-size: 30px; 
    font-family: 'Space Grotesk';
    color: #f6f6f6;
    font-weight: 900;
    text-align: left;
    line-height: 1;
    padding-bottom: 10px;
}

.btc_text {
    font-size: 14px; 
    font-family: 'Space Grotesk';
    color: #f7931a;
    font-weight: bold;
    text-align: left;
    line-height: 0.2;
    padding-top: 10px;
}

.eth_text {
    font-size: 14px; 
    font-family: 'Space Grotesk';
    color: #a1a1a1;
    font-weight: bold;
    text-align: left;
    line-height: 0.2;
    padding-top: 10px;
}

.xmr_text {
    font-size: 14px; 
    font-family: 'Space Grotesk';
    color: #ff6b08;
    font-weight: bold;
    text-align: left;
    line-height: 0.2;
    padding-top: 10px;
}

.sol_text {
    font-size: 14px; 
    font-family: 'Space Grotesk';
    color: #807af4;
    font-weight: bold;
    text-align: left;
    line-height: 0.2;
    padding-top: 10px;
}

.xrp_text {
    font-size: 14px; 
    font-family: 'Space Grotesk';
    color: #01acf1;
    font-weight: bold;
    text-align: left;
    line-height: 0.2;
    padding-top: 10px;
}

</style>
""", unsafe_allow_html=True)


#######################
# Function to connect to GCP MySQL
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

# Donut chart
def make_donut(input_response, input_text, input_color):
  if input_color == 'blue':
      chart_color = ['#29b5e8', '#155F7A']
  if input_color == 'green':
      chart_color = ['#27AE60', '#12783D']
  if input_color == 'orange':
      chart_color = ['#F39C12', '#875A12']
  if input_color == 'red':
      chart_color = ['#E74C3C', '#781F16']
    
  source = pd.DataFrame({
      "Topic": ['', input_text],
      "% value": [100-input_response, input_response]
  })
  source_bg = pd.DataFrame({
      "Topic": ['', input_text],
      "% value": [100, 0]
  })
    
  plot = alt.Chart(source).mark_arc(innerRadius=45, cornerRadius=25).encode(
      theta="% value",
      color= alt.Color("Topic:N",
                      scale=alt.Scale(
                          #domain=['A', 'B'],
                          domain=[input_text, ''],
                          # range=['#29b5e8', '#155F7A']),  # 31333F
                          range=chart_color),
                      legend=None),
  ).properties(width=130, height=130)
    
  text = plot.mark_text(align='center', color="#29b5e8", font="Lato", fontSize=32, fontWeight=700, fontStyle="italic").encode(text=alt.value(f'{input_response} %'))
  plot_bg = alt.Chart(source_bg).mark_arc(innerRadius=45, cornerRadius=20).encode(
      theta="% value",
      color= alt.Color("Topic:N",
                      scale=alt.Scale(
                          # domain=['A', 'B'],
                          domain=[input_text, ''],
                          range=chart_color),  # 31333F
                      legend=None),
  ).properties(width=130, height=130)
  return plot_bg + plot + text

#######################
# Load data from GCP MySQL


# def execute_stored_procedure(connection, procedure_name, table_name, percentage):
#     cursor = connection.cursor(buffered=True)
#     # Add multi=True to handle multiple statements in the stored procedure
#     cursor.execute(f"CALL {procedure_name}('{table_name}', {percentage})", multi=True)
    
#     df = None 

#     # Fetch all results from the cursor
#     for result in cursor:
#         df = pd.DataFrame(result.fetchall())
#         break  # We only need the first result set (assuming there's only one relevant result set)
    
#     return df

@st.cache_data # Apply caching to data loading function
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

@st.cache_data # Apply caching to data loading function
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

@st.cache_data # Apply caching to data loading function
def load_filtered_data_from_gcpLimit(table, filter_condition):
    # Connect to your GCP MySQL database
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None
    
    # Construct the query with the specified filter condition
    query = f"SELECT * FROM {table} WHERE {filter_condition} LIMIT 15000"
    print(f"############################### SELECT * FROM {table} LIMIT")
    
    # Execute the query and load the data into a DataFrame
    df = pd.read_sql(query, connection)
    connection.close()
    
    return df

@st.cache_data # Apply caching to data loading function
def GetTop10FrequentTxids():
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None, None, None, None, None, None, None
    
    cursor = connection.cursor()
    stored_proc_call = "CALL GetTop10FrequentTxids()"
    
    for result in cursor.execute(stored_proc_call, multi=True):
        if result.with_rows:
            results = result.fetchall()
            column_names = [desc[0] for desc in result.description]
            transactions_df = pd.DataFrame(results, columns=column_names)
            return (transactions_df)
    connection.close()
    
    return (transactions_df)

@st.cache_data # Apply caching to data loading function
def load_BTC_legends():
    # Connect to your GCP MySQL database
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None
    
    # Construct the query with the specified filter condition
    query = f"SELECT max(out_BTC_max) 	,min(in_BTC_max)    ,max(num_output_addresses)    ,max(num_input_addresses)    ,max(out_BTC_total) FROM 5200Group7.txs_features;"
    print(f"############################### load_BTC_legends")
     
    # Execute the query and load the data into a DataFrame
    df = pd.read_sql(query, connection)
    connection.close()
    
    return df

# @st.cache_data # Apply caching to data loading function
def GetTopTxByClass(selected_class):
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None, None, None, None, None, None, None
    
    cursor = connection.cursor()
    stored_proc_call = "CALL GetTopTxByClass(%s)"
    
    for result in cursor.execute(stored_proc_call, (selected_class,), multi=True):
        if result.with_rows:
            results = result.fetchall()
            column_names = [desc[0] for desc in result.description]
            transactions_df = pd.DataFrame(results, columns=column_names)
            return (transactions_df)
    connection.close()
    
    return (transactions_df)

@st.cache_data # Apply caching to data loading function
def CountTransactionsByClass():
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None, None, None, None, None, None, None
    
    cursor = connection.cursor()
    stored_proc_call = "CALL CountTransactionsByClass()"
    
    for result in cursor.execute(stored_proc_call, multi=True):
        if result.with_rows:
            results = result.fetchall()
            column_names = [desc[0] for desc in result.description]
            transactions_df = pd.DataFrame(results, columns=column_names)
            return (transactions_df)
    connection.close()
    
    return (transactions_df)

@st.cache_data # Apply caching to data loading function
def MostTransactedIllicitAddresses():
    connection = connect_to_gcp_mysql()
    if connection is None:
        return None, None, None, None, None, None, None
    
    cursor = connection.cursor()
    stored_proc_call = "CALL MostTransactedIllicitAddresses()"
    
    for result in cursor.execute(stored_proc_call, multi=True):
        if result.with_rows:
            results = result.fetchall()
            column_names = [desc[0] for desc in result.description]
            transactions_df = pd.DataFrame(results, columns=column_names)
            return (transactions_df)
    connection.close()
    
    return (transactions_df)




#######################

# Main app function
def app():

    # @st.cache_data # Apply caching to data loading function
    def GetTop10WalletsByTxid(txid):
        connection = connect_to_gcp_mysql()
        if connection is None:
            return None, None, None, None, None, None, None
        
        cursor = connection.cursor()
        stored_proc_call = "CALL GetTop10WalletsByTxid(%s)"
        
        for result in cursor.execute(stored_proc_call, (txid,), multi=True):
            if result.with_rows:
                results = result.fetchall()
                column_names = [desc[0] for desc in result.description]
                transactions_df = pd.DataFrame(results, columns=column_names)
                return (transactions_df)
        connection.close()
        
        return (transactions_df)
    # Sidebar for filtering options

    with st.sidebar:
        st.title('₿ Bitcoin Transactions Dashboard')

        # Dropdown for selecting percentage of data to load
        percentage_options = [2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
        selected_percentage = st.selectbox('Select Percentage of Data to Load', percentage_options, index=0)


    # Load the data from GCP MySQL
    # (transactions_df
    # # , classes_df, features_df,
    # #  txs_edgelist_df, wallets_df, wallet_classes_df,
    # #  wallet_features_df
    #  ) 
    

    btc_legends = load_BTC_legends()
    max_out_BTC_max = btc_legends['max(out_BTC_max)'].iloc[0]
    min_in_BTC_max = btc_legends['min(in_BTC_max)'].iloc[0]
    max_num_output_addresses = btc_legends['max(num_output_addresses)'].iloc[0]
    max_num_input_addresses = btc_legends['max(num_input_addresses)'].iloc[0]
    max_out_BTC_total = btc_legends['max(out_BTC_total)'].iloc[0]

            
    title_col, emp_col, btc_col, eth_col, xmr_col, sol_col, xrp_col = st.columns([1,0.2,1,1,1,1,1])

    with title_col:
        st.markdown('<p class="dashboard_title">Crypto<br>Dashboard</p>', unsafe_allow_html = True)

    with btc_col:
        with st.container():
            btc_price = max_out_BTC_max
            st.markdown(f'<p class="btc_text">Max Sent BTC<br></p><p class="price_details">{btc_price}</p>', unsafe_allow_html = True)

    with eth_col:
        with st.container():
            eth_price = min_in_BTC_max
            st.markdown(f'<p class="eth_text">Min Received BTC<br></p><p class="price_details">{eth_price}</p>', unsafe_allow_html = True)

    with xmr_col:
        with st.container():
            xmr_price = max_num_output_addresses
            st.markdown(f'<p class="xmr_text">Max Receiver Count<br></p><p class="price_details">{xmr_price}</p>', unsafe_allow_html = True)

    with sol_col:
        with st.container():
            sol_price = max_num_input_addresses
            st.markdown(f'<p class="sol_text">Max Sender Count<br></p><p class="price_details">{sol_price}</p>', unsafe_allow_html = True)

    with xrp_col:
        with st.container():
            xrp_price = max_out_BTC_total
            st.markdown(f'<p class="xrp_text">Max Total BTC Sent<br></p><p class="price_details">{xrp_price}</p>', unsafe_allow_html = True)




    # transactions_df = load_data_from_gcp('Transactions',selected_percentage)

    classes_df = load_data_from_gcp('classes',100)
    # features_df = load_data_from_gcp('txs_features',selected_percentage)
    # txs_edgelist_df = load_data_from_gcp('txs_edgelist',selected_percentage)
    # wallets_df = load_data_from_gcp('wallets',selected_percentage)
    wallet_classes_df = load_data_from_gcp('wallet_classes',100)
    # wallet_features_df = load_data_from_gcp('wallets_features',selected_percentage)
    # TxAddr_edgelist_df = load_data_from_gcp('TxAddr_edgelist',selected_percentage)
    # Transaction_details_df = load_data_from_gcp('Transaction_details',selected_percentage)
    # Addr_Vol_Combined_df = load_data_from_gcp('Addr_Vol_Combined',selected_percentage)
    # AddrTx_edgelist_df = load_data_from_gcp('AddrTx_edgelist',selected_percentage)
    # AddrAddr_edgelist_df = load_data_from_gcp('AddrAddr_edgelist',selected_percentage)

    # Load the data from GCP MySQL with the selected percentage
    transactions_df = load_data_from_gcp('Transactions', selected_percentage)
    print("############################### loaded transactions_df")
    print(len(transactions_df))

    # Ensure transactions_df is not None before proceeding
    if transactions_df is not None and not transactions_df.empty:
        # Get the list of txids from transactions_df
        txid_list = transactions_df['txId'].tolist()

        # Load features_df filtered by txid in transactions_df
        # features_df = load_filtered_data_from_gcp('txs_features')  # Load all features
        # features_df = features_df[features_df['txId'].isin(txid_list)]  # Filter by txid
        features_df = load_filtered_data_from_gcp('txs_features', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded features_df")
        print(len(features_df))

        # Load txs_edgelist_df filtered by txid in transactions_df
        # txs_edgelist_df = load_data_from_gcp('txs_edgelist')  # Load all edgelist
        # txs_edgelist_df = txs_edgelist_df[(txs_edgelist_df['txId1'].isin(txid_list)) | 
        #                                 (txs_edgelist_df['txId2'].isin(txid_list))]  # Filter by txid
        txs_edgelist_df = load_filtered_data_from_gcp('txs_edgelist', f'txId1 IN ({",".join(map(str, txid_list))}) OR txId2 IN ({",".join(map(str, txid_list))})')
        print("############################### loaded txs_edgelist_df")
        print(len(txs_edgelist_df))

        # Load TxAddr_edgelist_df filtered by txid in transactions_df
        # TxAddr_edgelist_df = load_data_from_gcp('TxAddr_edgelist')  # Load all edgelist
        # TxAddr_edgelist_df = TxAddr_edgelist_df[TxAddr_edgelist_df['txId'].isin(txid_list)]  # Filter by txid
        TxAddr_edgelist_df = load_filtered_data_from_gcpLimit('TxAddr_edgelist', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded TxAddr_edgelist_df")
        print(len(TxAddr_edgelist_df))

        # Load Transaction_details_df filtered by txid in transactions_df
        # Transaction_details_df = load_data_from_gcp('Transaction_details')  # Load all details
        # Transaction_details_df = Transaction_details_df[Transaction_details_df['txId'].isin(txid_list)]  # Filter by txid
        Transaction_details_df = load_filtered_data_from_gcp('Transaction_details', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded Transaction_details_df")
        print(len(Transaction_details_df))

        # Load Addr_Vol_Combined_df filtered by txid in transactions_df
        # Addr_Vol_Combined_df = load_data_from_gcp('Addr_Vol_Combined')  # Load all volume data
        # Addr_Vol_Combined_df = Addr_Vol_Combined_df[Addr_Vol_Combined_df['txId'].isin(txid_list)]  # Filter by txid
        Addr_Vol_Combined_df = load_filtered_data_from_gcpLimit('Addr_Vol_Combined', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded Addr_Vol_Combined_df")
        print(len(Addr_Vol_Combined_df))

        # Load AddrTx_edgelist_df filtered by txid in transactions_df
        # AddrTx_edgelist_df = load_data_from_gcp('AddrTx_edgelist')  # Load all edgelist
        # AddrTx_edgelist_df = AddrTx_edgelist_df[AddrTx_edgelist_df['txId'].isin(txid_list)]  # Filter by txid
        AddrTx_edgelist_df = load_filtered_data_from_gcp('AddrTx_edgelist', f'txId IN ({",".join(map(str, txid_list))})')
        print("############################### loaded AddrTx_edgelist_df")
        print(len(AddrTx_edgelist_df))

        # Now fetch AddrAddr_edgelist_df based on addresses in Addr_Vol_Combined_df and AddrTx_edgelist_df
        input_addresses = AddrTx_edgelist_df['input_address'].unique()
        output_addresses = TxAddr_edgelist_df['output_address'].unique()
        combined_addresses = set(input_addresses).union(set(output_addresses))

        # AddrAddr_edgelist_df = load_data_from_gcp('AddrAddr_edgelist')  # Load all edgelist
        # AddrAddr_edgelist_df = AddrAddr_edgelist_df[
        #     AddrAddr_edgelist_df['input_address'].isin(combined_addresses) | 
        #     AddrAddr_edgelist_df['output_address'].isin(combined_addresses)
        # ]
        addraddr_filter_condition = f'input_address IN ({",".join(map(repr, combined_addresses))}) OR output_address IN ({",".join(map(repr, combined_addresses))})'
        
        # Load filtered AddrAddr_edgelist_df using the new function
        AddrAddr_edgelist_df = load_filtered_data_from_gcpLimit('AddrAddr_edgelist', addraddr_filter_condition)
        print("############################### loaded AddrAddr_edgelist_df")
        print(len(AddrAddr_edgelist_df))

        # Fetch wallets_df based on addresses in AddrAddr_edgelist_df
        wallet_addresses = AddrAddr_edgelist_df['input_address'].unique().tolist() + AddrAddr_edgelist_df['output_address'].unique().tolist()
        # wallets_df = load_data_from_gcp('wallets')  # Load all wallets
        # wallets_df = wallets_df[wallets_df['address'].isin(wallet_addresses)]  # Filter by address
        wallets_filter_condition = f'address IN ({",".join(map(repr, wallet_addresses))})'
        # Load filtered wallets_df using the new function
        wallets_df = load_filtered_data_from_gcpLimit('wallets', wallets_filter_condition)
        print("############################### loaded wallets_df")
        print(len(wallets_df))

        # Finally, load wallet_features_df filtered by addresses in wallets_df
        wallet_feature_addresses = wallets_df['address'].tolist()
        # wallet_features_df = load_data_from_gcp('wallets_features', selected_percentage)  # Load features
        # wallet_features_df = wallet_features_df[wallet_features_df['address'].isin(wallet_feature_addresses)]  # Filter by address
        wallet_features_filter_condition = f'address IN ({",".join(map(repr, wallet_feature_addresses))})'

        # Load filtered wallet_features_df using the new function
        wallet_features_df = load_filtered_data_from_gcpLimit('wallets_features', wallet_features_filter_condition)
        print("############################### loaded wallet_features_df")
        print(len(wallet_features_df))
    # Input_AddrTx_Vol_Combined
    # Output_TxAddr_Vol_Combined
    # Metadata_Licit_Class2_Combined
    # Metadata_Illicit_Class1_Combined

        

    

    
  

    #######################
    # Dashboard Main Panel
    col = st.columns((1.5, 6, 2), gap='medium')

    with col[0]:
        st.markdown('#### Most Active')

        # Combine txId counts from AddrTx_edgelist_df (input_address) and TxAddr_edgelist_df (output_address)
        # Count txIds for input addresses
        input_tx_counts = AddrTx_edgelist_df.groupby('input_address')['txId'].nunique().reset_index()
        input_tx_counts.columns = ['address', 'tx_count']

        # Count txIds for output addresses
        output_tx_counts = TxAddr_edgelist_df.groupby('output_address')['txId'].nunique().reset_index()
        output_tx_counts.columns = ['address', 'tx_count']

        # Combine input and output tx counts for each address
        combined_tx_counts = pd.concat([input_tx_counts, output_tx_counts], axis=0)

        # Group by address and sum up tx counts
        wallet_tx_counts = combined_tx_counts.groupby('address')['tx_count'].sum().reset_index()

        # Sort by tx_count in descending order to find top 2 wallets
        sorted_wallets = wallet_tx_counts.sort_values(by='tx_count', ascending=False).reset_index(drop=True)

        # Check if sorted_wallets has data
        if not sorted_wallets.empty:
            # Get top 2 wallets
            top_1_wallet = sorted_wallets.iloc[0]
            top_2_wallet = sorted_wallets.iloc[1]

            # Extract required values
            first_state_name = top_1_wallet['address']
            first_state_population = top_1_wallet['tx_count']
            # first_state_delta = top_1_wallet['tx_count'] - top_2_wallet['tx_count']
            first_state_delta = int(top_1_wallet['tx_count'] - top_2_wallet['tx_count']) 

            st.metric(label=first_state_name, value=first_state_population, delta=first_state_delta)
        else:
            st.write("No wallet data available.")

        st.markdown('#### Largest Sender')
        # Sort Addr_Vol_Combined_df by total_sent in descending order
        sorted_wallets_by_total_sent = Addr_Vol_Combined_df.sort_values(by='total_sent', ascending=False).reset_index(drop=True)

        if not sorted_wallets_by_total_sent.empty:
            # Get top 2 wallets based on total_sent
            top_1_wallet = sorted_wallets_by_total_sent.iloc[0]
            top_2_wallet = sorted_wallets_by_total_sent.iloc[1]

            # Extract required values
            first_state_name = top_1_wallet['address']
            first_state_population = int(top_1_wallet['total_sent'])
            first_state_delta = int(top_1_wallet['total_sent'] - top_2_wallet['total_sent'])

            st.metric(label=first_state_name, value=first_state_population, delta=first_state_delta)
        else:
            st.write("No wallet data available.")

        st.markdown('#### Class Stats')
        illicit_count = 0
        licit_count = 0
        uk_count = 0
        # print(TransactionsByClass)
        # Extract the relevant counts
        TransactionsByClass = CountTransactionsByClass()
        illicit_row = TransactionsByClass[TransactionsByClass['name'] == 'illicit']
        licit_row = TransactionsByClass[TransactionsByClass['name'] == 'licit']
        uk_row = TransactionsByClass[TransactionsByClass['name'] == 'unknown']

        illicit_count = illicit_row['count'].values[0]
        licit_count = licit_row['count'].values[0]
        uk_count = uk_row['count'].values[0]

        total_count = illicit_count + licit_count + uk_count

        # Calculate percentages
        illicit_percent = round((illicit_count / total_count) * 100)
        licit_percent = round((licit_count / total_count) * 100)

        donut_illicit = make_donut(illicit_percent, 'Illicit', 'red')
        donut_licit = make_donut(licit_percent, 'Licit', 'green')

        migrations_col = st.columns((0.2, 1, 0.2))
        with migrations_col[1]:
            st.write('Licit')
            st.altair_chart(donut_licit)
            st.write('Illicit')
            st.altair_chart(donut_illicit)

    with col[1]:
        st.write('### Trends of Bitcoin Transactions Over Time')
        # Convert first_transaction_date to datetime and extract the date part for grouping
        Addr_Vol_Combined_df['date'] = pd.to_datetime(Addr_Vol_Combined_df['first_transaction_date']).dt.date

        # Group by first_transaction_date and sum the values
        grouped_data = Addr_Vol_Combined_df.groupby('date').agg({
            'total_received': 'sum',
            'total_sent': 'sum',
            'total_transactions': 'sum'
        }).reset_index()

        # Plotting
        plt.figure(figsize=(14, 7))
        

        # Convert the 'date' column back to a string format for plotting
        grouped_data['date'] = pd.to_datetime(grouped_data['date'])

        # Plot total_received
        plt.plot(grouped_data['date'], grouped_data['total_received'], label='Total Received', color='blue')

        # Plot total_sent
        plt.plot(grouped_data['date'], grouped_data['total_sent'], label='Total Sent', color='orange')

        # Plot total_transactions
        plt.plot(grouped_data['date'], grouped_data['total_transactions'], label='Total Transactions', color='green')

        # Adding titles and labels
        # plt.title('Trends of Bitcoin Transactions Over Time')
        plt.xlabel('Date of First Transaction')
        plt.ylabel('Amount')
        plt.legend()
        plt.grid()

        # Show the plot
        st.pyplot(plt)

        # Plotting
        plt.figure(figsize=(14, 7))
        # Total Volume Over Time
        st.write('### Total Volume Over Time')
        # Addr_Vol_Combined_df['date'] = pd.to_datetime(Addr_Vol_Combined_df['first_transaction_date']).dt.date
        # Addr_Vol_Combined_df['total_volume'] = Addr_Vol_Combined_df['total_volume']
        grouped_volume = Addr_Vol_Combined_df.groupby('date')['total_volume'].sum().reset_index()
        plt.plot(grouped_volume['date'], grouped_volume['total_volume'], label='Total Volume', color='purple')

        plt.xlabel('Date of First Transaction')
        plt.ylabel('Total Volume')
        plt.legend()
        plt.grid()

        # Show the plot
        st.pyplot(plt)

    with col[2]:
        st.markdown(f"### Count Transactions by Class")
        st.dataframe(TransactionsByClass)

        class_names = TransactionsByClass['name']
        class_counts = TransactionsByClass['count']

        # Create a bar plot
        plt.figure(figsize=(10, 6))
        plt.bar(class_names, class_counts, color=['orange', 'green', 'red'])
        plt.xlabel('Class')
        plt.ylabel('Count')
        plt.title('Count of Transactions by Class')
        plt.xticks(rotation=45)  # Rotate x-axis labels if necessary
        plt.grid(axis='y')

        # Show the plot
        st.pyplot(plt)

        st.markdown('#### Top Active Illicit Address')
        df_selected_year_sorted = MostTransactedIllicitAddresses()
        st.dataframe(df_selected_year_sorted,
                    column_order=("output_address", "tx_count"),
                    hide_index=True,
                    width=None,
                    column_config={
                        "output_address": st.column_config.TextColumn(
                            "Address",
                        ),
                        "tx_count": st.column_config.ProgressColumn(
                            "Count",
                            format="%f",
                            min_value=0,
                            max_value=max(df_selected_year_sorted.tx_count),
                        )}
                    )

    
    



    # Create two columns
    col = st.columns((2, 9), gap='medium')

    with col[0]:
        # Select a transaction ID to explore
        txId_list = list(classes_df['name'].unique())
        selected_class = st.selectbox('Select a Class', txId_list, index=0)
        class_mapping = {
            'illicit': 1,
            'licit': 2,
            'unknown': 3
        }
        mapped_class = class_mapping.get(selected_class)

        # Merge transaction data with class names and features based on txId and class
        transactions_merged = pd.merge(transactions_df, classes_df, on='class', how='left')
        transactions_merged = pd.merge(transactions_merged, features_df, on='txId', how='left')
        # print(transactions_merged.head())
        # print(f"---------> transactions_merged ")

        # Filter data for the selected transaction ID
        selected_tx_data = transactions_merged[transactions_merged['class'] == mapped_class]
        # print(f"---------> selected_tx_data = {selected_tx_data}")
    with col[1]:
        st.markdown(f"### Transaction with max in_BTC_max")
        print("############################### Transaction with max in_BTC_max")
        # print(len(max_in_btc_transaction))
        #st.dataframe(selected_tx_data)
        max_in_btc_transaction = GetTopTxByClass(selected_class)
        # Step 4: Find the transaction with the maximum in_BTC_max value
        if not max_in_btc_transaction.empty:
            #max_in_btc_transaction = filtered_data.loc[filtered_data['in_BTC_max'].idxmax()]
            st.dataframe(max_in_btc_transaction)  # Convert to DataFrame for display
        else:
            st.write("No data available for the selected class.")

    # Create two columns
    col = st.columns((2, 9), gap='medium')

    with col[0]:
        # top_10_txids = GetTop10FrequentTxids()
        # # Step 1: Count occurrences of txId1
        # txid1_counts = txs_edgelist_df['txId1'].value_counts().reset_index()
        # txid1_counts.columns = ['txid', 'count']

        # # Step 2: Count occurrences of txId2
        # txid2_counts = txs_edgelist_df['txId2'].value_counts().reset_index()
        # txid2_counts.columns = ['txid', 'count']

        # # Step 3: Combine both counts
        # combined_counts = pd.concat([txid1_counts, txid2_counts])

        # # Step 4: Group by txid and sum counts
        # total_counts = combined_counts.groupby('txid').sum().reset_index()

        # # Step 5: Sort by total_count in descending order and limit to top 10
        # top_10_txids = total_counts.sort_values(by='count', ascending=False).head(10)
        top_10_txids = max_in_btc_transaction['txId']

        

        selected_txId = st.selectbox('Top 10 Transaction Id', top_10_txids, index=0)
    with col[1]:
        st.markdown(f"### Top 10 Wallets for {selected_txId}")
        GetTop10WalletsByTxid = GetTop10WalletsByTxid(selected_txId)
        st.dataframe(GetTop10WalletsByTxid)


    

    #######################
    # Additional Information Section

    with st.expander('About', expanded=True):
        st.write("""
            - Data Source: Blockchain Analysis Dataset.
            - This dashboard allows you to explore Bitcoin transactions and wallet activity.
            - You can filter by specific transaction IDs or wallet addresses to view detailed information.
            """)

# Call the app function to run the dashboard logic
if __name__ == "__main__":
    app()
    