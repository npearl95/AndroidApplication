/*
Programmer: Chau Nguyen
Lab2
Android application containing 2 activities. The first activity contains TextViews to collect a
user’s name, email, a password, and a password confirmation. It also contains a Save button.
The second activity is a rudimentary Welcome screen that just displays a message containing the user’s
name, welcoming them to the app. e.g. “Welcome, Jane Dough, to the SignUpForm App”
 */
package edu.temple.signupform;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormActivity extends Activity {

    private EditText et_name, et_email, et_password, et_confirm;
    private String name, email, password, confirm;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        et_name = (EditText) findViewById(R.id.nameInput);
        et_email = (EditText) findViewById(R.id.emailInput);
        et_password= (EditText) findViewById(R.id.passwordInput);
        et_confirm = (EditText) findViewById(R.id.confirmPasswordInput);


        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                register();
            }
        });

    }
    public void register(){
        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        confirm = et_confirm.getText().toString().trim();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()+=])(?=\\S+$).{4,}$";

        if(name.isEmpty()||email.isEmpty()||password.isEmpty()||confirm.isEmpty()){
            Toast.makeText(this,"Error: One of the columns is left empty!",Toast.LENGTH_LONG).show();
        }
        else if(!email.matches(emailPattern))
        {
            Toast.makeText(this,"Error: Invalid email", Toast.LENGTH_LONG).show();
            et_email.setText("");
            et_email.requestFocus();

        }
        else if(!password.matches(PASSWORD_PATTERN))
        {
            Toast.makeText(this,"Error: Invalid Password:The password must be at least 8 characters long and include a number, lowercase letter, uppercase letter and special character (e.g. @, &, #, ?)", Toast.LENGTH_LONG).show();
            et_password.setText("");
            et_confirm.setText("");
            et_password.requestFocus();
        }
        else if(!password.equals(confirm)) {
            Toast.makeText(this, "Error: Password does not match the confirmation!", Toast.LENGTH_LONG).show();
            et_password.setText("");
            et_confirm.setText("");
            et_password.requestFocus();
        }
        else{
            Toast.makeText(this,"Success! Your credentials are saved!",Toast.LENGTH_LONG).show();
            Intent i;
            i = new Intent(FormActivity.this, Welcome.class);
            i.putExtra("key",name);
            startActivity(i);

        }
    }
}
