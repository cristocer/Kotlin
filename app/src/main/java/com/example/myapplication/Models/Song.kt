package com.example.myapplication.Models

class Song {

    var id:Int=0
    var name:String?=null
    var artist:String?=null
    var mode:String?=null
    var attempted:Int?=null
    var fullName:String=""
    var guessName:Int=0
    var guessArtist:Int=0
    var lyricsC:Int=0
    constructor(){}

    constructor(id:Int,name:String,artist:String,mode:String,attempted:Int,fullName:String){
        this.id=id
        this.name=name
        this.artist=artist
        this.mode=mode
        this.attempted=attempted
        this.fullName=fullName

    }





}