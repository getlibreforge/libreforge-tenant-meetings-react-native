import 'reflect-metadata';
import { ReactNode } from 'react';
import { injectable } from 'inversify';
import {ComponentCategory, IPages, InspectorControlEnum} from "@libreforge/libreforge-framework-shared"
import { IComponent, IComponents } from "@libreforge/libreforge-framework-shared"
import { BiImage } from "react-icons/bi";
import ImageComponent from './ImageComponent';
import { StandardComponentProvider } from '@libreforge/libreforge-framework';

@injectable()
export class ImageProvider extends StandardComponentProvider {
  
  type = 'Image';

  getCategory(): ComponentCategory {
    return "basic";
  }

  getIcon() {
    return <BiImage/>
  }

  getName() {
    return this.type;
  }

  getComponent(component: IComponent, pageComponents: IComponents, pages: IPages, 
    designMode: boolean, designModeInteractivityDisabled: boolean, 
    forwardedProps: any, overridenComponentPageState: any, collectionRefIdx: number | undefined): ReactNode {

    return (
      <ImageComponent
        pageComponents={pageComponents} collectionRefIdx={collectionRefIdx}
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
