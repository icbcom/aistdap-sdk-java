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

package ru.icbcom.aistdapsdkjava.impl.datastore.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import ru.icbcom.aistdapsdkjava.impl.datastore.auth.service.AuthenticationService;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private final AuthenticationService authenticationService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (authenticationService.isAuthenticated()) {
            request.getHeaders().add("Authorization", "Bearer " + authenticationService.getTokens().getAccessToken());
        }
        return execution.execute(request, body);
    }
}
