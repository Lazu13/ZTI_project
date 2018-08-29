import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import {Select} from 'antd';
import MyGroupMatchPredictions from './MyGroupMatchPredictions';
import MyKnockoutMatchPredictions from './MyKnockoutMatchPredictions'
import './MyMatchPredictions.css';

class MyPredictions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isGroup: true
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(value) {
        if (value === "group") {
            this.setState({isGroup: true});
        } else {
            this.setState({isGroup: false});
        }
    }

    render() {
        return (
            <div className="select-container">
                <Select defaultValue="group" onChange={this.handleChange}>
                    <Select.Option value="group">Group matches</Select.Option>
                    <Select.Option value="knockout">Knockout matches</Select.Option>
                </Select>
                {
                    this.state.isGroup ?
                        <MyGroupMatchPredictions/> : <MyKnockoutMatchPredictions/>
                }
            </div>
        )
    }
}

export default withRouter(MyPredictions)