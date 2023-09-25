package com.example.roomy.dataobject

data class Message(
    var firstName: String = "",
    var lastName: String = "",
    var image: Int,
    var lastMessage: String = "",
    var senderId: String? = null

){
    // Custom constructor
    constructor(image: Int) : this("", "", image, "", null)

    // Additional custom constructor
    constructor(firstName: String, lastName: String, image: Int) : this(firstName, lastName, image, "", null)
}
