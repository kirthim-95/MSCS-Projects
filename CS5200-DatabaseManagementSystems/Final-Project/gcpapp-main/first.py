import streamlit as st
import Dashboard, EDA, csv_analysis, transactionInfo

def main():
    # Welcome message and images
    

    # Page navigation using sidebar dropdown
    page_names = ["Dashboard","Explore Data", "CSV data", "Transaction analysis"]
    selected_page = st.sidebar.selectbox("Select a Page", page_names)

    if selected_page == "Dashboard":
        Dashboard.app()
    elif selected_page == "Explore Data":
        EDA.app()
    elif selected_page == "CSV data":
        csv_analysis.app()
    elif selected_page == "Transaction analysis":
        transactionInfo.app()

if __name__ == "__main__":
    main()