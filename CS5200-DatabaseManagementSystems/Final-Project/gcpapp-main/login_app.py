import streamlit as st
import mysql.connector
import bcrypt
from dotenv import load_dotenv
import os

load_dotenv()
 
# Access variables
MYSQL_HOST = os.getenv('MYSQL_HOST')
MYSQL_USER = os.getenv('MYSQL_USER')
MYSQL_PASSWORD = os.getenv('MYSQL_PASSWORD')
MYSQL_DATABASE = os.getenv('MYSQL_DATABASE')

# Database connection function
def create_connection():
    return mysql.connector.connect(
        host=MYSQL_HOST,
        user=MYSQL_USER,
        password=MYSQL_PASSWORD,
        database=MYSQL_DATABASE,
        auth_plugin='mysql_native_password'
    )

# Function to validate user credentials
def validate_user(username, password):
    connection = create_connection()
    cursor = connection.cursor(dictionary=True)
    try:
        # Execute query to fetch the user
        cursor.execute("SELECT * FROM User WHERE username = %s", (username,))
        user = cursor.fetchone()  # Fetch the first result
        if user:
            # Compare provided password with hashed password in the database
            if bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
                return True
        return False
    finally:
        # Explicitly consume unread results to avoid InternalError
        # cursor.fetchall()  # Clear any remaining results (if any)
        cursor.close()
        connection.close()

# Streamlit App
def main():
    st.title("Login Page")

    # Input fields
    username = st.text_input("Username")
    password = st.text_input("Password", type="password")

    # Login button
    if st.button("Login"):
        if validate_user(username, password):
            st.success("Login successful!")
            # Inside your login logic after successful validation
            st.success("Login successful!")
            st.session_state.login_successful = True  # Set flag for successful login

        else:
            st.error("Invalid username or password.")
    
    # Link or button to navigate to registration page
    if st.button("Go to Register Page"):
        st.session_state.page = "register"
        st.rerun()

if __name__ == "__main__":
    main()