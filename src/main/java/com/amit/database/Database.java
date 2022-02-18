/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.util.ArrayList;

/**
 *
 * @author unbxd
 */
public class Database {
    
    ArrayList<Table> tableList = new ArrayList<Table>();

    public Database(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }
}
