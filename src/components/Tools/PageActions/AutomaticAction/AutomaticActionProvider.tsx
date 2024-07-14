import 'reflect-metadata';
import { ReactElement, ReactNode } from 'react';
import { injectable } from 'inversify';
import {ComponentCategory, IPages, InspectorControlEnum} from "@libreforge/libreforge-framework-shared"
import { IComponent, IComponents } from "@libreforge/libreforge-framework-shared"
import { VscGithubAction } from "react-icons/vsc";
import { StandardComponentProvider } from '@libreforge/libreforge-framework';
import AutomaticActionExecutorComponent from './AutomaticActionComponent';

@injectable()
export class AutomaticActionProvider extends StandardComponentProvider {

  type = 'Automatic Action';

  getCategory(): ComponentCategory {
    return "tools";
  }

  getIcon() {
    return <VscGithubAction />
  }

  getName() {
    return this.type;
  }

  getComponent(component: IComponent, pageComponents: IComponents, pages: IPages, 
      designMode: boolean, designModeInteractivityDisabled: boolean, 
      forwardedProps: any, overridenComponentPageState: any, collectionRefIdx: number | undefined,
      wrapperComponent?: ReactElement, wrapperContainer?: ReactElement): ReactNode {

    return (
      <AutomaticActionExecutorComponent 
        componentId={component.id} pageComponents={pageComponents} 
        designMode={designMode} pages={pages} collectionRefIdx={collectionRefIdx}
        wrapperComponent={wrapperComponent} wrapperContainer={wrapperContainer}
        {...component.props} {...forwardedProps}
      />
    );
  }

  getInspectorControls(): { control: InspectorControlEnum; props: any }[] {
    return [ 
      {
        control: InspectorControlEnum.ActionGroupControl,
        props: { name: 'actionGroup', label: 'Actions' },
      },      
      {
        control: InspectorControlEnum.SwitchControl,
        props: { name: 'isJustOnce', label: 'Just Once?' },
      },
      {
        control: InspectorControlEnum.TextControl,
        props: { name: 'seconds', label: 'Every (X) sec' },
      },      
    ];
  }

  getDefaultProps() {
    return { w: '28px', color: 'messenger.900' };
  }

  isContainer() {
    return false;
  }
}
