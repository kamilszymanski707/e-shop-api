package pl.kamilszymanski707.eshopapi.lib.utilslib.constant

class ClientConstant {

    companion object {

        private const val URI_PREFIX = "lb://"
        private const val URI_SUFFIX = "/graphql"
        const val CATALOG_SERVICE_URI = URI_PREFIX + "catalog" + URI_SUFFIX
        const val DISCOUNT_SERVICE_URI = URI_PREFIX + "discount" + URI_SUFFIX
    }
}