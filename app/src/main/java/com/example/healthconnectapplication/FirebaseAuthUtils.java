package com.example.healthconnectapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthUtils {

    private FirebaseAuth mAuth;

    public FirebaseAuthUtils() {
        // Initialize Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Gets the email of the currently logged-in user.
     * @return The email of the logged-in user, or null if no user is logged in.
     */
    public String getLoggedInUserEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            return user.getEmail();  // Return the logged-in user's email
        }
        return null;  // Return null if no user is logged in
    }

    /**
     * Gets the current logged-in user.
     * @return FirebaseUser object representing the logged-in user, or null if no user is logged in.
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Signs out the current user from Firebase Authentication.
     */
    public void signOut() {
        mAuth.signOut();  // Sign out the current user
    }

    /**
     * Checks if a user is logged in.
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }
}
