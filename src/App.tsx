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
import { TenantScriptExtension } from './script/ext/TenantScriptExtension';
import { TextProvider } from './components/Label/Text/TextProvider';
import { ImageProvider } from './components/Image';
import KeepAwake from 'react-native-keep-awake';
import { VariableTextProvider } from './components/Label/VariableText';

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

frameworkBindProviders(container);
componentBindProviders(container);

container.bind<AbstractAction>(SYMBOL_ACTION_PROVIDER).to(SimpleAlertAction);
container.bind<AbstractAction>(SYMBOL_ACTION_PROVIDER).to(RNRouteToPageAction);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(ContainerProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(AutomaticActionProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(InputProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(TextProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(ImageProvider);
container.bind<ComponentProvider>(SYMBOL_COMPONENT_PROVIDER).to(VariableTextProvider);

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
