import React, { ComponentClass, FunctionComponent, ReactElement, forwardRef, memo } from 'react';
import { IComponents, IPages } from '@libreforge/libreforge-framework-shared';
import { cleanupCustomComponentProps, usePropsOverrideByComponentRef } from '@libreforge/libreforge-framework';
import ChildComponentRenderer from '@libreforge/libreforge-framework/dist/components/ChildComponentRenderer';
import { Dimensions, ImageBackground, StyleSheet, View } from 'react-native';

type ContainerComponentProps = {
  componentId: string;
  overridenComponentPageState: any;
  collectionRefIdx: number | undefined;
  children: string[];
  pageComponents: IComponents;
  pages: IPages;
  designMode: boolean;
  designModeInteractivityDisabled: boolean,
  wrapperComponent?: ReactElement; wrapperContainer?: ReactElement;
}

const getStyles = (props: any) => StyleSheet.create({
  containerRow: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    gap: 24,
    ...props
  },
  image: {
    flex: 1,
    justifyContent: 'center',
    width: '100%'
  }
});

const ContainerComponent = forwardRef((props: ContainerComponentProps, ref) => {
  const { componentId, overridenComponentPageState, collectionRefIdx, children, pageComponents, pages, designMode, designModeInteractivityDisabled, wrapperComponent, wrapperContainer } = props;
  
  const styles = getStyles(props);
  const component = pageComponents[componentId];
  const backgroundImage = component.props['backgroundImage'];

  // const formErrorProps = { ...useFormControlError(type, children, pageComponents, designMode, props), pos: 'relative', ref };
  // const sharedErrorProps = useFormControlSharedError(type, children, pageComponents, designMode, formErrorProps);
  // const overridenProps = usePropsOverrideByComponentRef(props.componentId, sharedErrorProps, props.designMode);
  // const cleanedProps = cleanupCustomComponentProps(overridenProps);  

  const childContent = children.map((key: string) => {
    return (
      <ChildComponentRenderer key={key} componentName={key} overridenComponentPageState={overridenComponentPageState}
        designMode={designMode} designModeInteractivityDisabled={designModeInteractivityDisabled}
        pageComponents={pageComponents} collectionRefIdx={collectionRefIdx} 
        pages={pages} wrapperComponent={wrapperComponent} wrapperContainer={wrapperContainer} />        
    )
  })

  return (
    <View style={styles.containerRow}>
      {!!backgroundImage && (
        <ImageBackground source={{ uri: backgroundImage }} resizeMode="cover" style={styles.image}>
          { childContent }
        </ImageBackground>
      )}
      {!backgroundImage && (
          childContent
      )}
    </View>    
  )
});

export default memo(ContainerComponent);
