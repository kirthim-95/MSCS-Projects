import streamlit as st
import login_app
import register_app


# Initialize session state variables
if 'logged_in' not in st.session_state:
    st.session_state.logged_in = False

if 'page' not in st.session_state:
    st.session_state.page = "login"

def main():
    # Determine which page to display based on session state
    if not st.session_state.logged_in:
        if st.session_state.page == "login":
            login_app.main()  # Call the main function from login_app.py
            
            # Check if login was successful
            if st.session_state.get('login_successful', False):
                st.session_state.logged_in = True
                st.session_state.page = "features"
                st.rerun()

        elif st.session_state.page == "register":
            register_app.main()  # Call the main function from register-app.py
            
            # Check if registration was successful
            if st.session_state.get('registration_successful', False):
                st.session_state.page = "login"
                st.rerun()

    else:
        import first
        # User is logged in, show the features graph page
        first.main()  # Call the main function from features-graph.py

if __name__ == "__main__":
    main()
