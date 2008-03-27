package uk.ltd.getahead.dwr;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

/**
 * Control who should be accessing which methods on which classes.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public interface AccessControl
{
    /**
     * Check the method for accessibility, and return an error message if
     * anything is wrong. If nothing is wrong, return null.
     * This is not a great becuase it mixes 2 bits of information in the same
     * variable (is it wrong, and what is wrong) but without multi-value returns
     * in Java this seems like the most simple implementation.
     * @param req The request from which we work out roles
     * @param creator Where does the method come from?
     * @param className The Javascript name of the class
     * @param method What is the method to execute?
     * @return null if nothing is wrong or an error message
     */
    public String getReasonToNotExecute(HttpServletRequest req, Creator creator, String className, Method method);

    /**
     * J2EE role based security allows us to restrict methods to only being used
     * by people in certain roles.
     * @param scriptName The name of the creator to Javascript
     * @param methodName The name of the method (without brackets)
     * @param role The new role name to add to the list for the given scriptName and methodName
     */
    public void addRoleRestriction(String scriptName, String methodName, String role);

    /**
     * Add an include rule.
     * Each creator can have either a list of inclusions or a list of exclusions
     * but not both. If a creator has a list of inclusions then the default
     * policy is to deny any method that is not specifically included. If the
     * creator has a list of exclusions then the default policy is to allow
     * any method not listed.
     * If there are no included or excluded rules then the default policy is to
     * allow all methods
     * @param scriptName The name of the creator to Javascript
     * @param methodName The name of the method (without brackets)
     */
    public void addIncludeRule(String scriptName, String methodName);

    /**
     * Add an exclude rule.
     * @param scriptName The name of the creator to Javascript
     * @param methodName The name of the method (without brackets)
     * @see AccessControl#addIncludeRule(String, String)
     */
    public void addExcludeRule(String scriptName, String methodName);
}