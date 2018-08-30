import React, {Component} from 'react';
import {signup} from '../../util/APIUtils';
import './Signup.css';
import {Link} from 'react-router-dom';
import {
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../../constants';

import {Form, Input, Button, notification} from 'antd';

const FormItem = Form.Item;

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: {
                value: ''
            },
            email: {
                value: ''
            },
            password: {
                value: ''
            }
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const signupRequest = {
            email: this.state.email.value,
            username: this.state.username.value,
            password: this.state.password.value
        };
        signup(signupRequest)
            .then(response => {
                notification.success({
                    message: 'ZTI',
                    description: "Zostałeś poprawnie zarejestrowany. Kontynuuj, aby sie zalogować!",
                });
                this.props.history.push("/login");
            }).catch(error => {
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            });
        });
    }

    isFormInvalid() {
        return !(this.state.username.validateStatus === 'success' &&
            this.state.email.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success'
        );
    }

    render() {
        return (
            <div className="signup-container">
                <h1 className="page-title">Rejestracja</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem label="Username"
                                  hasFeedback
                                  validateStatus={this.state.username.validateStatus}
                                  help={this.state.username.errorMsg}>
                            <Input
                                size="large"
                                name="username"
                                autoComplete="off"
                                placeholder="Nazwa użytkownika"
                                value={this.state.username.value}
                                onBlur={this.checkUsername}
                                onChange={(event) => this.handleInputChange(event, this.validateUsername)}/>
                        </FormItem>
                        <FormItem
                            label="Email"
                            hasFeedback
                            validateStatus={this.state.email.validateStatus}
                            help={this.state.email.errorMsg}>
                            <Input
                                size="large"
                                name="email"
                                type="email"
                                autoComplete="off"
                                placeholder="Email"
                                value={this.state.email.value}
                                onBlur={this.checkEmail}
                                onChange={(event) => this.handleInputChange(event, this.validateEmail)}/>
                        </FormItem>
                        <FormItem
                            label="Password"
                            validateStatus={this.state.password.validateStatus}
                            help={this.state.password.errorMsg}>
                            <Input
                                size="large"
                                name="password"
                                type="password"
                                autoComplete="off"
                                placeholder="Hasło (od 6 do 20 znaków)"
                                value={this.state.password.value}
                                onChange={(event) => this.handleInputChange(event, this.validatePassword)}/>
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    className="signup-form-button"
                                    disabled={this.isFormInvalid()}>Zarejestruj</Button>
                            Jesteś już zarejestrowany? <Link to="/login">Zaloguj się</Link>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }

    // Validation Functions

    checkUsername = () => {
        // First check for client side errors in username
        const usernameValue = this.state.username.value;
        const usernameValidation = this.validateUsername(usernameValue);

        if (usernameValidation.validateStatus === 'error') {
            this.setState({
                username: {
                    value: usernameValue,
                    ...usernameValidation
                }
            });
            return;
        }

        this.setState({
            username: {
                value: usernameValue,
                validateStatus: 'success',
                errorMsg: null
            }
        });
    };

    checkEmail = () => {
        // First check for client side errors in email
        const emailValue = this.state.email.value;
        const emailValidation = this.validateEmail(emailValue);

        if (emailValidation.validateStatus === 'error') {
            this.setState({
                email: {
                    value: emailValue,
                    ...emailValidation
                }
            });
            return;
        }

        this.setState({
            email: {
                value: emailValue,
                validateStatus: 'success',
                errorMsg: null
            }
        });
    };


    validateEmail = (email) => {
        if (!email) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email nie może być pusty'
            }
        }

        const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
        if (!EMAIL_REGEX.test(email)) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email nie jest poprawny'
            }
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Email jest zbyt długi (Maksymalna dłogść to ${EMAIL_MAX_LENGTH})`
            }
        }

        return {
            validateStatus: null,
            errorMsg: null
        }
    };

    validateUsername = (username) => {
        if (username.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Nazwa użytkownika jest zbyt krótka. (Minimalna długość nazwy to ${USERNAME_MIN_LENGTH})`
            }
        } else if (username.length > USERNAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Nazwa użytkownika jest zbyt długa (Maksymalna długość nazwy to ${USERNAME_MAX_LENGTH})`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    };

    validatePassword = (password) => {
        if (password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Hasło jest zbyt krótkie (Minmalna długość hasła to ${PASSWORD_MIN_LENGTH})`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Hasło jest zbyt długie (Maksymalna długość hasła to ${PASSWORD_MAX_LENGTH})`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    }

}

export default Signup;