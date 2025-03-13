## Bitcoin Transactions Dashboard
==============================

### Overview
--------

The Bitcoin Transactions Dashboard is a web application built using Streamlit that provides an interactive platform for exploring and analyzing Bitcoin transaction data. This application allows users to gain insights into transaction patterns, wallet activities, and illicit transaction behaviors through various data visualizations and analysis tools.

### Features
--------

-   **User Authentication**: Users can register and log in to access the dashboard securely.

-   **Dashboard Navigation**: The app features a sidebar for easy navigation between different sections, including:

    -   **Dashboard**: A summary of key metrics and visualizations related to Bitcoin transactions.
    -   **Explore Data**: An exploratory data analysis (EDA) section where users can view and analyze data tables.
    -   **CSV Data Analysis**: Users can upload CSV files to correlate with existing database tables for additional insights.
    -   **Transaction Analysis**: A detailed overview of illicit transactions, including related transaction IDs and addresses.
-   **Data Visualization**: The app utilizes libraries such as Altair, Plotly, and Matplotlib to create visually appealing charts and graphs, including:

    -   Donut charts to represent the distribution of illicit and licit transactions.
    -   Line plots to visualize trends in Bitcoin transactions over time.
    -   Scatter plots and heatmaps for correlation analysis between different transaction attributes.
-   **Database Integration**: The app connects to a MySQL database hosted on Google Cloud Platform (GCP) to retrieve and manipulate transaction data, ensuring that users have access to up-to-date information.

-   **User Notes**: Users can add personal notes related to specific transactions, allowing for customized tracking of important insights.

-   **Interactive Graphs**: The app provides network visualizations of transactions, allowing users to explore relationships between different transaction IDs and addresses.

### Technologies Used
-----------------

-   **Streamlit**: For building the web application interface.
-   **MySQL**: For data storage and retrieval.
-   **Python Libraries**:
    -   `pandas` for data manipulation.
    -   `numpy` for numerical operations.
    -   `matplotlib`, `seaborn`, and `altair` for data visualization.
    -   `plotly` for interactive visualizations.
    -   `networkx` for graph-based analysis of transactions.
    -   `bcrypt` for secure password hashing.
    -   `python-dotenv` for managing environment variables.

### Installation
------------

To run the app locally, follow these steps:

1.  Clone the repository:

    bash

    Insert CodeEditCopy code

    `1git clone <repository-url>  2cd  <repository-folder>`

2.  Install the required packages:

    bash

    Insert CodeEditCopy code

    `1pip install -r requirements.txt`

3.  Set up your MySQL database and update the `.env` file with your database credentials:

    Insert CodeEditCopy code

    `1MYSQL_HOST=<your-mysql-host>  2MYSQL_USER=<your-mysql-user>  3MYSQL_PASSWORD=<your-mysql-password>  4MYSQL_DATABASE=<your-database-name>`

4.  Run the Streamlit app:

    bash

    Insert CodeEditCopy code

    `1streamlit run app.py`

### Usage
-----

-   Once the app is running, navigate to the provided URL in your web browser.
-   Register for an account or log in with your existing credentials.
-   Use the sidebar to navigate through the different sections of the app.
-   Explore the various visualizations and analyses to gain insights into Bitcoin transactions.

### Conclusion
----------

The Bitcoin Transactions Dashboard serves as a powerful tool for analyzing Bitcoin transactions, enabling users to identify trends, patterns, and illicit activities in the cryptocurrency space. Whether you are a researcher, analyst, or cryptocurrency enthusiast, this app provides valuable insights into the world of Bitcoin transactions.