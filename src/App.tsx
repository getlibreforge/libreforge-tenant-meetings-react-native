import React, { useState } from 'react';

import { RematchDispatch, init } from '@rematch/core';
import { Provider, useDispatch as useReduxDispatch } from 'react-redux';
import { app, InversifyContainerProviderContext, bindProviders as frameworkBindProviders } from '@libreforge/libreforge-framework';
import pages from './config/application.json'
import { Container } from 'inversify';
import { bindProviders as componentBindProviders, Application, LocalImageManager, SYMBOL_LOCAL_IMAGE_MANAGER } from '@libreforge/libreforge-framework-react-native';
import { AbstractAction, SYMBOL_ACTION_PROVIDER, AbstractScriptExtension, SYMBOL_SCRIPT_EXTENSION } from '@libreforge/libreforge-framework';
import { RNRouteToPageAction } from './actions/RNRouteToPageAction';
import { SecureStorageScriptExtension } from './script/ext/SecureStorageScriptExtension';
import { ComponentProvider, SYMBOL_COMPONENT_PROVIDER, NavigationCurrentPageProviderContext } from '@libreforge/libreforge-framework';
import { TenantScriptExtension } from './script/ext/TenantScriptExtension';
import KeepAwake from 'react-native-keep-awake';
import { CustomLocalImageManager } from './service/CustomLocalImageManager';

/* Redux Store configuration */
const models = { app };
export const storeConfig = {
  models,
  plugins: [],
};

export const useDispatch = () => {
  return useReduxDispatch() as RematchDispatch<typeof models>;
};

// @ts-ignore
const store = init(storeConfig);

const container = new Container();
container.bind<AbstractScriptExtension>(SYMBOL_SCRIPT_EXTENSION).to(SecureStorageScriptExtension);
container.bind<AbstractScriptExtension>(SYMBOL_SCRIPT_EXTENSION).to(TenantScriptExtension);
container.bind<LocalImageManager>(SYMBOL_LOCAL_IMAGE_MANAGER).to(CustomLocalImageManager);

frameworkBindProviders(container);
componentBindProviders(container);

container.bind<AbstractAction>(SYMBOL_ACTION_PROVIDER).to(RNRouteToPageAction);

function App(): React.JSX.Element {

  const [currentRoute, setCurrentRoute] = useState<string | undefined>("home")
  KeepAwake.activate();

  return (
    <Provider store={store}>
      <InversifyContainerProviderContext.Provider value={container}>
        <NavigationCurrentPageProviderContext.Provider value={{ currentRoute, setCurrentRoute }}>
          <Application
            pages={pages}
            routeToUrl={undefined}
          />
        </NavigationCurrentPageProviderContext.Provider>
      </InversifyContainerProviderContext.Provider>
    </Provider>
  );
}

export default App;
