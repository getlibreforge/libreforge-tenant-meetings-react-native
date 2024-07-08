import React from 'react';

import { RematchDispatch, init } from '@rematch/core';
import { Provider, useDispatch as useReduxDispatch } from 'react-redux';
import { app, InversifyContainerProviderContext, bindProviders as frameworkBindProviders } from '@libreforge/libreforge-framework';
import pages from './config/application.json'
import { Container } from 'inversify';
import { bindProviders as componentBindProviders, Application } from '@libreforge/libreforge-framework-react-native';

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
frameworkBindProviders(container);
componentBindProviders(container);

function App(): React.JSX.Element {
  return (
    <Provider store={store}>
      <InversifyContainerProviderContext.Provider value={container}>
        <Application
            pages={pages}
            routeToUrl={undefined}
        />
      </InversifyContainerProviderContext.Provider>
    </Provider>
  );
}

export default App;
