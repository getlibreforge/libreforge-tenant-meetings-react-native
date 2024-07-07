import {StyleSheet, Text, View} from 'react-native';
import React from 'react';
import {connect} from 'react-redux';
import {Dispatch, RootState} from '../store.ts';
import Button from '../components/Button.tsx';

const mapState = (state: RootState) => ({
  count: state.count,
});

const mapDispatch = (dispatch: Dispatch) => ({
  increment: () => dispatch.count.increment(1),
  incrementAsync: () => dispatch.count.incrementAsync(1),
});

type StateProps = ReturnType<typeof mapState>;
type DispatchProps = ReturnType<typeof mapDispatch>;
type Props = StateProps & DispatchProps;

const CountScreen = (props: Props) => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>The count is: {props.count}</Text>
      <View style={styles.actions}>
        <Button title="Increment" onPress={() => props.increment()} />
        <Button
          title="Increment Async"
          onPress={() => props.incrementAsync()}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    gap: 24,
  },
  text: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  actions: {
    gap: 8,
  },
});

export default connect(mapState, mapDispatch)(CountScreen);
