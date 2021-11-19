package com.example.work4me_app

class Company(companyName:String) {
    private val _companyName : String = companyName

    fun getCompanyName():String{return this._companyName}

}