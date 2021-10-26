package com.example.neighbourproject.user

class EvaluationHelper {
    companion object{
        fun evaluatePassword(password: String): String?{
            // Strip away whitespace
            var pwd = password.replace(" ", "")

            //6 caracters, atleast onle lover and one uppercase
            return if(pwd.matches("""^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,}$""".toRegex()))
                pwd
            else
                null
        }

        fun evaluateUsername(username: String): String?{
            // Strip away whitespace
            var user = username.replace(" ", "")

            //6 caracters, atleast onle lover and one uppercase
            return if(user.matches("""[a-zA-Z\d]{1,}@[a-zA-Z\d]{1,}\.[a-zA-Z]{1,}""".toRegex()))
                user
            else
                null
        }
    }
}
