import { forwardRef } from 'react';
import { IComponents, IPages } from '@libreforge/libreforge-framework-shared';
import { cleanupCustomComponentProps, useActionHandlers, usePropsOverrideByComponentRef } from '@libreforge/libreforge-framework';
import {Text, StyleSheet} from 'react-native';
import {useNavigation} from '@react-navigation/native';

const getStyles = (props: any) => StyleSheet.create({
  text: {
    fontSize: props.fontSize || 14,
    // lineHeight: props.lineHeight || 21,
    // fontWeight: props.fontWeight || 'bold',
    letterSpacing: props.letterSpacing || 0.25,
    color: props.color || 'white',
    ...cleanupCustomComponentProps(props, { key: 'key' })
  },
});

const TextComponent = forwardRef((props: { componentId: string, pages: IPages, designMode: boolean, 
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

  return (
    <Text style={styles.text} onPress={() => targetProps.onClick(undefined)}>
      {props.children || ''}
    </Text>
  );      
});

export default TextComponent;
