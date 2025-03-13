# MSCS Projects

Welcome to the MSCS Projects repository! This repository houses my assignments and projects from various courses I have taken as part of my Master’s in Computer Science. Below are the key projects that demonstrate my skills in programming, database management, data visualization, and web development.

## Table of Contents
- [CS5010 - Programming Design Paradigms](#cs5010---programming-design-paradigms)
  - [Assignments](#assignments)
  - [Projects](#projects)
    - [Birds Conservatory](#birds-conservatory)
    - [Games](#games)
- [CS5200 - Database Management Systems](#cs5200---database-management-systems)
  - [Bitcoin Transactions Dashboard](#bitcoin-transactions-dashboard)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Conclusion](#conclusion)

## CS5010 - Programming Design Paradigms

This section contains all the assignments and two projects from the CS5010 course. These assignments are focused on developing a deeper understanding of software design paradigms.

### Assignments
This folder contains 8 assignments completed as part of the course, covering various programming topics, including:

- Object-Oriented Design
- Functional Programming
- Data Structures and Algorithms
- Design Patterns
- Code Refactoring

### Projects

#### Birds Conservatory
- **Description:** A software application designed to help manage a bird conservatory. It allows users to track birds, their feeding schedules, and their habitats.
- **Technologies Used:** Java, OOP Principles
- **Challenges:** Ensuring scalability and handling complex data relations between birds, food, and their habitats.

#### Games
- **Description:** A project that includes the development of multiple simple games using Java.
- **Technologies Used:** Java, Design Patterns
- **Challenges:** Playing around Object Oriented Programming through Design Patterns.

---

## CS5200 - Database Management Systems

### Bitcoin Transactions Dashboard

**Overview**  
The Bitcoin Transactions Dashboard is a web application built using Streamlit that provides an interactive platform for exploring and analyzing Bitcoin transaction data. This application allows users to gain insights into transaction patterns, wallet activities, and illicit transaction behaviors through various data visualizations and analysis tools.

### Features
- **User Authentication:** Secure registration and login for users.
- **Dashboard Navigation:** Sidebar for easy navigation between different sections:
  - **Dashboard:** Key metrics and visualizations related to Bitcoin transactions.
  - **Explore Data:** EDA section to explore data tables.
  - **CSV Data Analysis:** Upload CSV files for correlation with the existing database.
  - **Transaction Analysis:** Analysis of illicit transactions, transaction IDs, and addresses.
- **Data Visualization:** Interactive charts created using Altair, Plotly, and Matplotlib.
  - Donut charts for transaction distribution.
  - Line plots for trends over time.
  - Scatter plots and heatmaps for correlation analysis.
- **Database Integration:** Connects to MySQL on GCP for real-time data analysis.
- **Interactive Graphs:** Visualizations of transaction networks and relationships.

### Technologies Used
- **Streamlit**: Web app framework.
- **MySQL**: Database for storing Bitcoin transaction data.
- **Python Libraries:**
  - pandas for data manipulation
  - numpy for numerical operations
  - matplotlib, seaborn, and altair for data visualization
  - plotly for interactive charts
  - networkx for graph analysis
  - bcrypt for password hashing
  - python-dotenv for environment variable management
