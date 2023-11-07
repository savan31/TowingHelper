package codes.tuton.urvesh.towinghelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerify extends AppCompatActivity {

    private TextView textView_intent;
    private TextView textview_countdown;
    private EditText editText_otp;

    String mVerificationId ;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        textView_intent = findViewById(R.id.textview_intent);
        textview_countdown = findViewById(R.id.textview_countdown);
        editText_otp = findViewById(R.id.edittext_otp);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String u_mobileNo = intent.getStringExtra("user_mobileNo");

        textView_intent.setText("Verify "+u_mobileNo);

        sendVerificationCode(u_mobileNo);


    }






    public void sendVerificationCode(String mNumber) { //mNumber. = countryCode + number
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

        editText_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6) {
                    verifyVerificationCode(s.toString());
                }
            }
        });


    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS in mobile
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //verifying the code

                editText_otp.setText(code);
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpVerify.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(OtpVerify.this, "OTP is Send Please Verify ", Toast.LENGTH_SHORT).show();
             mVerificationId = s;

            textview_countdown.setVisibility(View.VISIBLE);
            new CountDownTimer(60000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                    textview_countdown.setText(String.valueOf( (millisUntilFinished/1000)));

                }

                @Override
                public void onFinish() {
                }
            }.start();

        }
    };



    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpVerify.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(OtpVerify.this,Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(OtpVerify.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void resend(View view){

        Intent intent = getIntent();
        String u_mobileNo = intent.getStringExtra("user_mobileNo");
        sendVerificationCode(u_mobileNo);


    }

}
