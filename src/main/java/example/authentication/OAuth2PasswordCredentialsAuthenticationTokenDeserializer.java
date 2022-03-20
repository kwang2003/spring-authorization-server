package example.authentication;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest.Builder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class OAuth2PasswordCredentialsAuthenticationTokenDeserializer extends JsonDeserializer<OAuth2PasswordCredentialsAuthenticationToken> {
 private static final TypeReference<List<GrantedAuthority>> GRANTED_AUTHORITY_LIST = new TypeReference<List<GrantedAuthority>>() {
 };

 private static final TypeReference<Object> OBJECT = new TypeReference<Object>() {
 };

 static final TypeReference<Set<String>> STRING_SET = new TypeReference<Set<String>>() {
 };

 static final TypeReference<Map<String, Object>> STRING_OBJECT_MAP = new TypeReference<Map<String, Object>>() {
 };

 /**
  * This method construct {@link OAuth2PasswordCredentialsAuthenticationToken} object from
  * serialized json.
  * @param jp the JsonParser
  * @param ctxt the DeserializationContext
  * @return the user
  * @throws IOException if a exception during IO occurs
  * @throws JsonProcessingException if an error during JSON processing occurs
  */
 @Override
 public OAuth2PasswordCredentialsAuthenticationToken deserialize(JsonParser jp, DeserializationContext ctxt)
         throws IOException, JsonProcessingException {
  ObjectMapper mapper = (ObjectMapper) jp.getCodec();
  JsonNode jsonNode = mapper.readTree(jp);
  Boolean authenticated = readJsonNode(jsonNode, "authenticated").asBoolean();
  JsonNode principalNode = readJsonNode(jsonNode, "principal");
  Object principal = getPrincipal(mapper, principalNode);
  JsonNode credentialsNode = readJsonNode(jsonNode, "credentials");
  Object credentials = getCredentials(credentialsNode);
  List<GrantedAuthority> authorities = mapper.readValue(readJsonNode(jsonNode, "authorities").traverse(mapper),
          GRANTED_AUTHORITY_LIST);
  Set<String> scopes = findValue(jsonNode, "scopes", STRING_SET, mapper);
  Map<String, Object> additionalParameters = findValue(jsonNode, "additionalParameters", STRING_OBJECT_MAP, mapper);
  OAuth2PasswordCredentialsAuthenticationToken token = (!authenticated)
          ? new OAuth2PasswordCredentialsAuthenticationToken(null, scopes,additionalParameters)
          : new OAuth2PasswordCredentialsAuthenticationToken(null, scopes, additionalParameters);
  JsonNode detailsNode = readJsonNode(jsonNode, "details");
  if (detailsNode.isNull() || detailsNode.isMissingNode()) {
   token.setDetails(null);
  }
  else {
   Object details = mapper.readValue(detailsNode.toString(), OBJECT);
   token.setDetails(details);
  }

  return token;
 }

 private Object getCredentials(JsonNode credentialsNode) {
  if (credentialsNode.isNull() || credentialsNode.isMissingNode()) {
   return null;
  }
  return credentialsNode.asText();
 }

 private Object getPrincipal(ObjectMapper mapper, JsonNode principalNode)
         throws IOException, JsonParseException, JsonMappingException {
  if (principalNode.isObject()) {
   return mapper.readValue(principalNode.traverse(mapper), Object.class);
  }
  return principalNode.asText();
 }

 private JsonNode readJsonNode(JsonNode jsonNode, String field) {
  return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
 }

 static <T> T findValue(JsonNode jsonNode, String fieldName, TypeReference<T> valueTypeReference,
                        ObjectMapper mapper) {
  if (jsonNode == null) {
   return null;
  }
  JsonNode value = jsonNode.findValue(fieldName);
  return (value != null && value.isContainerNode()) ? mapper.convertValue(value, valueTypeReference) : null;
 }
}
