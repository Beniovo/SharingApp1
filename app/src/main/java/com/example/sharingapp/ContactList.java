package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {

    private ArrayList<Contact> contacts;
    private String FILENAME = "contacts.sav";

    public ContactList() {
        this.contacts = new ArrayList<Contact>();
    }

    public void setContacts (ArrayList<Contact> contact_list) {
        contacts = contact_list;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void addContact (Contact contact) {
        contacts.add(contact);
    }

    public void deleteContact (Contact contact) {
        contacts.remove(contact);
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public int getIndex(Contact contact) {
        int pos = 0;
        for (Contact c : contacts) {
            if (contact.getId().equals(c.getId())) {
                return pos;
            }
            pos = pos+1;
        }
        return -1;
    }

    public int getSize() {
        return contacts.size();
    }

    public void loadContacts(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllUsernames(){

        ArrayList<String> allusernames = new ArrayList<>();
        for(Contact c : contacts){
            allusernames.add(c.getUsername());
        }
        return allusernames;
    }

    public boolean hasContact(Contact contact){
           boolean hasAContact = true;
           if(contact.getUsername().isEmpty() || contact.getEmail().isEmpty()){
               hasAContact = false;
           }
return hasAContact;
    }

    public boolean isUsernameAvailable(String username){
        boolean usernameAvailable = true;
        for(Contact c : contacts){
            if(c.getUsername().equals(username)){
                usernameAvailable = false;
            }
        }
        return usernameAvailable;
    }

    public Contact getContactByUsername (String username){
        Contact contact = null;
        for(Contact c : contacts){
            if(c.getUsername().equalsIgnoreCase(username)){
                contact = c;
            }
        }
        return contact;
    }




}
