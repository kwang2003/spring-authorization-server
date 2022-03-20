package example.authentication;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class OAuth2ObjectModule extends SimpleModule {
    public OAuth2ObjectModule() {
        super(OAuth2ObjectModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(OAuth2PasswordCredentialsAuthenticationToken.class,
                OAuth2PasswordCredentialsAuthenticationTokenMixin.class);
    }
}
