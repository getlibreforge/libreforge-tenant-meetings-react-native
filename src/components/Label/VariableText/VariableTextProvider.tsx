import 'reflect-metadata';
import { ReactNode } from 'react';
import { injectable } from 'inversify';
import {ComponentCategory, IPages, InspectorControlEnum} from "@libreforge/libreforge-framework-shared"
import { IComponent, IComponents } from "@libreforge/libreforge-framework-shared"
import { TbBraces } from "react-icons/tb";
import VariableTextComponent from './VariableTextComponent';
import { StandardComponentProvider } from '@libreforge/libreforge-framework';

@injectable()
export class VariableTextProvider extends StandardComponentProvider {
  
  type = 'VariableText';

  getCategory(): ComponentCategory {
    return "basic";
  }

  getIcon() {
    return <TbBraces />
  }

  getName() {
    return this.type;
  }

  getComponent(component: IComponent, pageComponents: IComponents, pages: IPages, 
    designMode: boolean, designModeInteractivityDisabled: boolean, 
    forwardedProps: any, overridenComponentPageState: any, collectionRefIdx: number | undefined): ReactNode {

    return (
      <VariableTextComponent
        pageComponents={pageComponents} pages={pages} 
        overridenComponentPageState={overridenComponentPageState} collectionRefIdx={collectionRefIdx}
        designMode={designMode} designModeInteractivityDisabled={designModeInteractivityDisabled}
        {...component.props} {...forwardedProps} componentId={component.id}
      />
    );
  }

  getInspectorControls(): { control: InspectorControlEnum; props: any }[] {
    return [
    ];
  }

  getDefaultProps() {
    return {};
  }

  isContainer() {
    return false;
  }
}
