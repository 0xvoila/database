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
public class Table {
 
    ArrayList<Record> recordList = new ArrayList<Record>();

    public Table(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }
}
