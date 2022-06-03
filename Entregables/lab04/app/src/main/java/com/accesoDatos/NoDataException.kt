package com.accesoDatos

class NoDataException : Exception {
    /** Creates a new instance of NoDataException  */
    constructor() {}
    constructor(msg: String?) : super(msg) {}
}