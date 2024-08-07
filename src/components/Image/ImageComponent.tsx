import { forwardRef } from 'react';
import { IComponents, IPages } from '@libreforge/libreforge-framework-shared';
import { cleanupCustomComponentProps, useActionHandlers, usePropsOverrideByComponentRef } from '@libreforge/libreforge-framework';
import {Text, StyleSheet, Image, TouchableOpacity} from 'react-native';
import {useNavigation} from '@react-navigation/native';
import { LocalImageManager } from '../../service/LocalImageManager';

const getStyles = (props: any) => StyleSheet.create({
  image: {
    fontSize: props.fontSize || 14,
    ...props
  },
});

const ImageComponent = forwardRef((props: { componentId: string, pages: IPages, designMode: boolean, 
    pageComponents: IComponents, componentPage: string, collectionRefIdx: number | undefined, children: string | undefined }, ref) => {

  const navigation = useNavigation();
  const styles = getStyles(props);

  let targetProps: any = props;

  const actionGroup = props.pageComponents[props.componentId].actionGroup;
  targetProps = useActionHandlers(targetProps, actionGroup, navigation, undefined);  
  targetProps = usePropsOverrideByComponentRef(props.componentId, targetProps, props.designMode);

  // if (targetProps.leftIcon) {
  //   if (Object.keys(iconsList).includes(targetProps.leftIcon)) {
  //     const Icon = iconsList[targetProps.leftIcon as keyof typeof iconsList];
  //     targetProps.leftIcon = <Icon path="" />;
  //   } else {
  //     targetProps.leftIcon = undefined;
  //   }
  // }

  // if (targetProps.rightIcon) {
  //   if (Object.keys(iconsList).includes(targetProps.rightIcon)) {
  //     const Icon = iconsList[targetProps.rightIcon as keyof typeof iconsList];
  //     targetProps.rightIcon = <Icon path="" />;
  //   } else {
  //     targetProps.rightIcon = undefined;
  //   }
  // }

  // const elementProps = cleanupCustomComponentProps(targetProps)
  // return <Button ref={ref} {...elementProps} />;

  const localImageManager = new LocalImageManager();
  const source = targetProps['src'];

  return (
    <TouchableOpacity onPress={() => targetProps.onClick(undefined)}>
      <Image style={styles.image} source={ localImageManager.getImageSource(source) }/>
    </TouchableOpacity>
  );      
});

export default ImageComponent;
