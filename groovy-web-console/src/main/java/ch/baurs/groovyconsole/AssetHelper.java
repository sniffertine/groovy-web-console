package ch.baurs.groovyconsole;

class AssetHelper {


    public static String createAssetUrl(String path) {
        String assetPath = UrlHelper.joinSegmentsToPath(
                Constants.ASSETS_URL_PREFIX,
                path
        );

        return UrlHelper.createUrl(assetPath);
    }

}
