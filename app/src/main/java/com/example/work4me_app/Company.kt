package com.example.work4me_app

class Company(companyName:String, companyUid:String, pictureUrl:String?) {
    private val _companyName : String = companyName
    private val _companyUid : String = companyUid
    private val _pictureUrl : String? = pictureUrl

    fun getCompanyName():String{return this._companyName}

    fun getCompanyUid():String{return this._companyUid}

    fun getPictureUrl():String?{return this._pictureUrl}

}