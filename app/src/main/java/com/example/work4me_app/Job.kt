package com.example.work4me_app

class Job(id : String ,city: String, title: String, description: String, salary: Int, company : Company) {

    private var _id : String = id
    private var _city : String = city
    private var _title : String = title
    private var _description : String = description
    private var _salary : Int = salary
    private var _company : Company = company

    fun getId() : String {return this._id}

    fun getCity() : String {return this._city}

    fun setCity(city : String) {this._city = city}

    fun getTitle() : String {return this._title}

    fun setTitle(title : String) {this._title = title}

    fun getDescription() : String {return this._description}

    fun setDescription(description : String) {this._description = description}

    fun getSalary() : Int {return this._salary}

    fun setSalary(salary : Int) {this._salary = salary}

    fun getCompany():Company { return this._company }
}