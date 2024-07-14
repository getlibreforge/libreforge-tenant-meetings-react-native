import React, { useState } from 'react';

import { RematchDispatch, init } from '@rematch/core';
import { Provider, useDispatch as useReduxDispatch } from 'react-redux';
import { app, InversifyContainerProviderContext, bindProviders as frameworkBindProviders } from '@libreforge/libreforge-framework';
import pages from './config/application.json'
import { Container } from 'inversify';
import { bindProviders as componentBindProviders, Application } from '@libreforge/libreforge-framework-react-native';
import { AbstractAction, SYMBOL_ACTION_PROVIDER, AbstractScriptExtension, SYMBOL_SCRIPT_EXTENSION } from '@libreforge/libreforge-framework';
import { SimpleAlertAction } from './actions/SimpleAlertAction';
import { RNRouteToPageAction } from './actions/RNRouteToPageAction';
import { SecureStorageScriptExtension } from './script/ext/SecureStorageScriptExtension';
import { ComponentProvider, SYMBOL_COMPONENT_PROVIDER, NavigationCurrentPageProviderContext } from '@libreforge/libreforge-framework';
import { ContainerProvider } from './components/Container';
import { AutomaticActionProvider } from './components/Tools/PageActions/AutomaticAction';
import { InputProvider } from './components/Input';

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
container.bind<AbstractAction>(SYMBOL_ACTION_PROVIDER).to(SimpleAlertAction);
container.bind<AbstractAction>(SYMBOL_ACTION_PROVIDER).to(RNRouteToPageAction);
container.bind<AbstractScriptExtension>(SYMBOL_SCRIPT_EXTENSION).to(SecureStorageScriptExtension);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(ContainerProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(AutomaticActionProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(InputProvider);

frameworkBindProviders(container);
componentBindProviders(container);

function App(): React.JSX.Element {

  const [currentRoute, setCurrentRoute] = useState<string | undefined>("home")

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
