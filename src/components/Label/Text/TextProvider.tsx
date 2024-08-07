import 'reflect-metadata';
import { ReactNode } from 'react';
import { injectable } from 'inversify';
import {ComponentCategory, IPages, InspectorControlEnum} from "@libreforge/libreforge-framework-shared"
import { IComponent, IComponents } from "@libreforge/libreforge-framework-shared"
import { PiTextAa } from "react-icons/pi";
import { StandardComponentProvider } from '@libreforge/libreforge-framework';
import TextComponent from './TextComponent';

@injectable()
export class TextProvider extends StandardComponentProvider {
  
  type = 'Text';

  getCategory(): ComponentCategory {
    return "basic";
  }

  getIcon() {
    return <PiTextAa />
  }

  getName() {
    return this.type;
  }

  getComponent(component: IComponent, pageComponents: IComponents, pages: IPages, 
    designMode: boolean, designModeInteractivityDisabled: boolean, 
    forwardedProps: any, overridenComponentPageState: any, collectionRefIdx: number | undefined): ReactNode {

    return (
      <TextComponent
        componentId={component.id} pageComponents={pageComponents} 
        designMode={designMode} designModeInteractivityDisabled={designModeInteractivityDisabled}
        collectionRefIdx={collectionRefIdx}
        {...component.props} {...forwardedProps}
      />
    );
  }

  getInspectorControls(): { control: InspectorControlEnum; props: any }[] {
    return [
      { control: InspectorControlEnum.ChildrenControl, props: {} },
    ];
  }

  getDefaultProps() {
    return { children: 'Text value' };
  }

  isContainer() {
    return false;
  }
}
