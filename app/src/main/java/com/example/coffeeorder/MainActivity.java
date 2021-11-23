package com.example.coffeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity<99) {
            quantity = quantity + 1;
        }
        else
        {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity>1)
            quantity = quantity - 1;
        else
        {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }
    private int calculateprice(){
        return quantity*5;
    }
    public void submitOrder(View view) {
        CheckBox whippedcreamcheckbox=(CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean haswhippedcream =whippedcreamcheckbox.isChecked();
        CheckBox chocolate=(CheckBox) findViewById(R.id.notify_me_checkbox2);
        boolean haschocolate =chocolate.isChecked();
        int price=calculateprice();
        if(haschocolate)
            price=price+(2*quantity);
        if(haswhippedcream)
            price=price+(1*quantity);
        EditText text=(EditText)findViewById(R.id.name);
        String value=text.getText().toString();
        String priceMessage =createOrderSummary(value,price,haswhippedcream,haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for:"+value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(priceMessage);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
/**    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }**/

    private String createOrderSummary(String valueentered,int price, boolean addwhippedcream,boolean addchocolate)
    {
     String pricemessage="Name:"+valueentered;
        pricemessage+="\n Add WhippedCream: "+addwhippedcream;
        pricemessage+="\n Add Chocolate: "+addchocolate;
        pricemessage+="\n Quantity: "+quantity;
        pricemessage+="\n Total$: "+price;
        pricemessage+="\n Thank You!";
        return pricemessage;
    }
}
