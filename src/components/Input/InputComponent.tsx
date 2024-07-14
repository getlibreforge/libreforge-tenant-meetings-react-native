import { forwardRef, useContext } from 'react';
import { IComponents } from '@libreforge/libreforge-framework-shared';
import React from 'react';
import { InversifyContainerProviderContext, useHiddenByComponentRef, usePageStateValueByComponentRef, 
  AbstractValueChangeAction, SYMBOL_VALUE_CHANGE_ACTION, ProviderFactory, useDispatch, getCurrentPageState } from '@libreforge/libreforge-framework';
import { StyleSheet, TextInput } from 'react-native';
import { useSelector } from "react-redux";

const getStyles = (props: any) => StyleSheet.create({
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
    ...props
  },
});

const InputComponent = forwardRef(
  (props: { componentId: string; _x_name: string; pageComponents: IComponents; type: any, overridenComponentPageState: any }, ref) => {

    // const router = useNavigate();
    // const snackbar = useSnackbar();

    const dispatch = useDispatch();
    const currentPageState = useSelector(getCurrentPageState);

    const component = props.pageComponents[props.componentId];
    const componentProps = component.props;

    const container = useContext(InversifyContainerProviderContext);
    const factory = new ProviderFactory(container);
    const onChangeHandler = factory.getValueChangeHandlerByName(componentProps._x_onchange);

    const styles = getStyles(props);

    const value = usePageStateValueByComponentRef(props._x_name, props.overridenComponentPageState);
    const hidden = useHiddenByComponentRef(props._x_name, props.overridenComponentPageState);
    
    const actionGroup = props.pageComponents[props.componentId].actionGroup;
    // let propsElement = useActionHandlers({ ...props, ref, ...value, ...hidden }, actionGroup, undefined, undefined);
    // const elementProps = cleanupCustomComponentProps(propsElement)

    const elementProps = props;

    return <TextInput
      style={styles.input}
      onChangeText={async (value) => {
        await onChangeHandler?.execute(componentProps._x_name, [], value, currentPageState, dispatch);
      }}
      value={currentPageState[componentProps._x_name]}
      placeholder={componentProps.placeholder}
      // keyboardType="numeric"
    />
});

export default InputComponent;
