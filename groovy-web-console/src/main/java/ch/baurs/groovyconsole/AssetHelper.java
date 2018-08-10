package ch.baurs.groovyconsole;

class AssetHelper {


    public static String createAssetUrl(String path) {
        String assetPath = UrlHelper.joinSegmentsToPath(
                Application.getConfiguration().assetsUrlPrefix,
                path
        );

        return UrlHelper.createUrl(assetPath);
    }

}
