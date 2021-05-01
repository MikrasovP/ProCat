package vsu.csf.network

interface AuthHolder {

    /**
     * Always provides relevant token
     *
     * @return empty string if user is logged out
     */
    fun getToken() : String

}