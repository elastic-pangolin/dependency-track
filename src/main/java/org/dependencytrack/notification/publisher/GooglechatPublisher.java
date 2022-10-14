/*
 * COPY OF WEBHOOK PUBLISHER
 *
 * This file is part of Dependency-Track.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) Steve Springett. All Rights Reserved.
 */
package org.dependencytrack.notification.publisher;

import alpine.notification.Notification;
import alpine.common.logging.Logger;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import javax.json.JsonObject;

public class GooglechatPublisher extends AbstractWebhookPublisher implements Publisher {
    private static final PebbleEngine ENGINE = new PebbleEngine.Builder().defaultEscapingStrategy("json").build();
    private static final PebbleTemplate TEMPLATE = ENGINE.getTemplate("templates/notification/publisher/googlechat.peb");
    final Logger logger = Logger.getLogger(this.getClass());
    public void inform(final Notification notification, final JsonObject config) {
        try {
            //TEST
            final String content = prepareTemplate(notification, TEMPLATE);
            logger.info("GoogleChat-Template: " + content);
            //TEST-END
            publish(DefaultNotificationPublishers.GOOGLECHAT.getPublisherName(), TEMPLATE, notification, config);
        } catch (Exception e) {
            logger.error("Googlechat notification could not be created");
            return;
        }
        logger.info("Googlechat notification creation done");
    }
}