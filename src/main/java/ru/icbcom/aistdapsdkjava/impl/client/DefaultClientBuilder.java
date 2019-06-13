/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ru.icbcom.aistdapsdkjava.impl.client;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.Assert;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.ClientBuilder;

public class DefaultClientBuilder implements ClientBuilder {

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    private String baseUrl;
    private String login;
    private String password;

    @Override
    public ClientBuilder setLogin(String login) {
        Assert.notNull(login, "login cannot be null");
        Assert.isTrue(!login.isBlank(), "login cannon be blank");
        this.login = login;
        return this;
    }

    @Override
    public ClientBuilder setPassword(String password) {
        Assert.notNull(password, "password cannot be null");
        Assert.isTrue(!password.isBlank(), "password cannon be blank");
        this.password = password;
        return this;
    }

    @Override
    public ClientBuilder setBaseUrl(String baseUrl) {
        Assert.notNull(baseUrl, "baseUrl cannot be null");
        Assert.isTrue(urlValidator.isValid(baseUrl), "baseUrl should be valid");
        this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public Client build() {
        Assert.notNull(baseUrl, "AistDap base url must not be null");
        Assert.notNull(login, "AistDap login must not be null");
        Assert.notNull(password, "AistDap password must not be null");
        return new DefaultClient(baseUrl, login, password);
    }

}
