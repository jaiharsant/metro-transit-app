

package com.mts.validation;


import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.common.MetroTransisConstants;
import com.mts.exception.BusinessException;
import com.mts.exception.BusinessExceptionMessage;

import org.apache.commons.io.IOUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Component
public class RequestValidator {

    private ConcurrentHashMap<String, JSONObject> schemaStore = new ConcurrentHashMap<String, JSONObject>();

    /**
     * Load json from file.
     *
     * @param name the name
     * @return the JSON object
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static JSONObject loadJsonFromFile(final String name) throws IOException {
        InputStream inputStream = getInputStreamFromClassPath(name);
        return loadJsonFromStream(inputStream);
    }

    /**
     * Load json from stream.
     *
     * @param inputStream the input stream
     * @return the JSON object
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static JSONObject loadJsonFromStream(InputStream inputStream) throws IOException {
        JSONObject jsonObject = new JSONObject(new JSONTokener(inputStream));
        inputStream.close();
        return jsonObject;
    }

    public <T> T validate(String path, InputStream request, final Class<T> cl) throws IOException {
        ObjectMapper OBJMAPPER = new ObjectMapper();
        String jsonBody = null;
        JSONObject jsonSchemaObj;

        try {
            jsonBody = IOUtils.toString(request, "UTF-8");

            if (!schemaStore.contains(path)) {
                jsonSchemaObj = loadJsonFromFile(path);
                schemaStore.put(path, jsonSchemaObj);
            }
            else {
                jsonSchemaObj = schemaStore.get(path);
            }

            Schema schema = SchemaLoader.builder()
                                        .schemaJson(jsonSchemaObj)
                                        .build()
                                        .load().build();

            JSONObject jsonObjInput = new JSONObject(new JSONTokener(jsonBody));
            schema.validate(jsonObjInput);
        }
        catch (ValidationException exception) {
            throw new BusinessException(new BusinessExceptionMessage(MetroTransisConstants.VALIDATION_ERROR, exception.getErrorMessage()));
        }
        return OBJMAPPER.readValue(jsonBody, cl);
    }

    /**
     * Check for file existance at classpath.
     *
     * @param fileName the file name
     * @return true/false
     */
    public static boolean isFileFound(final String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        return resource.exists();
    }

    /**
     * Gets the input stream from class path.
     *
     * @param fileName the file name
     * @return the input stream from class path
     * @throws IOException
     */
    public static InputStream getInputStreamFromClassPath(final String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        return resource.getInputStream();
    }

    protected JSONObject getBodySchema(final String path) {
        return schemaStore.get(path);
    }
}
