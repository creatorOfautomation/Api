package properties;

public class PropertiesCollection {

    private static PropertiesProvider provider = new PropertiesProvider();

    public static final String BASE_URL = provider.getBaseURL();
    public static final String EXCEL_DATA_PATH = provider.getPathForExcelData();
    public static final String CONTENT_RESPONSE_TYPE = provider.getContentResponseType();

    public static final String HOST_DB = provider.getHostDataBase();
    public static final String PORT_DB = provider.getPortDataBase();
    public static final String USER_DB = provider.getUserDataBase();
    public static final String PASSWORD_DB = provider.getPasswordDataBase();
    public static final String DRIVER_DB = provider.getDriveDataBaseOracle();
    public static final String SCHEME_DB = provider.getSchemeDataBase();

}
