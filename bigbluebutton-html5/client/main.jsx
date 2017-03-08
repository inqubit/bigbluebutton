import React from 'react';
import { Meteor } from 'meteor/meteor';
import { render } from 'react-dom';
import { showModal } from '/imports/ui/components/app/service';
import { renderRoutes } from '../imports/startup/client/routes.js';
import { IntlProvider } from 'react-intl';
import Singleton from '/imports/ui/services/storage/local.js';
import AudioModalContainer  from '/imports/ui/components/audio-modal/container';

function loadUserSettings() {
    const userSavedFontSize = Singleton.getItem('bbbSavedFontSizePixels');

    if (userSavedFontSize) {
      document.getElementsByTagName('html')[0].style.fontSize = userSavedFontSize;
    }
}

function setAudio() {
  const LOG_CONFIG = Meteor.settings || {};
  let autoJoinAudio = LOG_CONFIG.public.app.autoJoinAudio;

  if (autoJoinAudio) {
    showModal( <AudioModalContainer /> );
  }
}

function setMessages(data) {
  let messages = data;
  let defaultLocale = 'en';

  render((
    <IntlProvider  locale={defaultLocale} messages={messages}>
      {renderRoutes()}
    </IntlProvider>
  ), document.getElementById('app'));

  setAudio();
}

Meteor.startup(() => {

  loadUserSettings();

  let browserLanguage = navigator.language; //set this manually to force localization in a specific language
  let request = new Request
    (`${window.location.origin}/html5client/locale?locale=${browserLanguage}`);

  fetch(request, { method: 'GET' })
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      setMessages(data);
    })
    .catch(function error(err) {
      console.log('request failed', err);
    });

});
