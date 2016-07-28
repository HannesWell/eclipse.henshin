/**
 * generated by Xtext 2.10.0
 */
package org.eclipse.emf.henshin.text.henshin_text;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getActiontype <em>Actiontype</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getUpdate <em>Update</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject
{
  /**
   * Returns the value of the '<em><b>Actiontype</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Actiontype</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Actiontype</em>' attribute.
   * @see #setActiontype(String)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getAttribute_Actiontype()
   * @model
   * @generated
   */
  String getActiontype();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getActiontype <em>Actiontype</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Actiontype</em>' attribute.
   * @see #getActiontype()
   * @generated
   */
  void setActiontype(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' reference.
   * @see #setName(EAttribute)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getAttribute_Name()
   * @model
   * @generated
   */
  EAttribute getName();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getName <em>Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' reference.
   * @see #getName()
   * @generated
   */
  void setName(EAttribute value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Expression)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getAttribute_Value()
   * @model containment="true"
   * @generated
   */
  Expression getValue();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Expression value);

  /**
   * Returns the value of the '<em><b>Update</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Update</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Update</em>' attribute.
   * @see #setUpdate(String)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getAttribute_Update()
   * @model
   * @generated
   */
  String getUpdate();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Attribute#getUpdate <em>Update</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Update</em>' attribute.
   * @see #getUpdate()
   * @generated
   */
  void setUpdate(String value);

} // Attribute
