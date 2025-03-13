import streamlit as st
import mysql.connector
import bcrypt
from datetime import datetime
from dotenv import load_dotenv
import os

load_dotenv()
 
# Access variables
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')

# Function to create a connection to the MySQL database
def create_connection():
    return mysql.connector.connect(
        host=MYSQL_HOST,
        user=MYSQL_USER,
        password=MYSQL_PASSWORD,
        database=MYSQL_DATABASE,
        auth_plugin='mysql_native_password'
    )

# Function to insert a new user into the database
def register_user(username, email, password):
    # Hash the password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    # Get current date for RegistrationDate
    registration_date = datetime.now().strftime('%Y-%m-%d')

    # Establish a connection to the database
    connection = create_connection()
    cursor = connection.cursor()

    # Insert user details into the User table
    insert_query = """
        INSERT INTO User (username, email, RegistrationDate, password)
        VALUES (%s, %s, %s, %s)
    """
    cursor.execute(insert_query, (username, email, registration_date, hashed_password.decode('utf-8')))
    connection.commit()

    cursor.close()
    connection.close()

# Streamlit UI
def main():
    st.title("Registration Page")

    # Collect user input for registration
    username = st.text_input("Username")
    email = st.text_input("Email")
    password = st.text_input("Password", type="password")

    # Register button
    if st.button("Register"):
        # Validate if fields are filled
        if username and email and password:
            try:
                # Register user in the database
                register_user(username, email, password)
                st.success("Registration successful!")
                st.session_state.registration_successful = True  # Set flag for successful registration
                # Redirect to login page
                # os.system("streamlit run login_app.py")
                # st.stop()  # Stop further execution of this script
            except Exception as e:
                st.error(f"Error occurred during registration: {e}")
        else:
            st.error("Please fill in all fields")

     # Button to navigate back to login page
    if st.button("Back to Login Page"):
        st.session_state.page = "login"
        st.rerun() 

if __name__ == "__main__":
    main()