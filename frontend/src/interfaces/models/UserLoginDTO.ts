/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface UserLoginDTO
 */
export interface UserLoginDTO {
    /**
     * 
     * @type {string}
     * @memberof UserLoginDTO
     */
    email: string;
    /**
     * 
     * @type {string}
     * @memberof UserLoginDTO
     */
    password: string;
}

/**
 * Check if a given object implements the UserLoginDTO interface.
 */
export function instanceOfUserLoginDTO(value: object): value is UserLoginDTO {
    if (!('email' in value) || value['email'] === undefined) return false;
    if (!('password' in value) || value['password'] === undefined) return false;
    return true;
}

export function UserLoginDTOFromJSON(json: any): UserLoginDTO {
    return UserLoginDTOFromJSONTyped(json, false);
}

export function UserLoginDTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserLoginDTO {
    if (json == null) {
        return json;
    }
    return {
        
        'email': json['email'],
        'password': json['password'],
    };
}

export function UserLoginDTOToJSON(json: any): UserLoginDTO {
    return UserLoginDTOToJSONTyped(json, false);
}

export function UserLoginDTOToJSONTyped(value?: UserLoginDTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'email': value['email'],
        'password': value['password'],
    };
}

