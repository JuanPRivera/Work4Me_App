package com.example.work4me_app

class Company(companyName:String, companyUid:String) {
    private val _companyName : String = companyName
    private val _companyUid : String = companyUid

    fun getCompanyName():String{return this._companyName}

    fun getCompanyUid():String{return this._companyUid}

}