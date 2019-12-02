package com.team4.lib.util;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.hal.util.BoundaryException;

/**
 * This PID Loop was taken from 254 in C++, then was converted to Java by Team4Element.
 * There is no Feed Forward
 * 
 * This class implements a PID Control Loop.
 * Does all computation synchronously (i.e. the calculate() function must be
 * called by the user from his own thread)
 */

public class SynchronusPID extends SendableBase implements Sendable {
	private boolean m_readOnly;
	
	private double m_P;
	private double m_I;
	private double m_D;
	
	private double m_maximumOutput = 1.0; // |maximum output|
	private double m_minimumOutput = -1.0; // |minimum output|
	private double m_maximumInput = 0.0; // maximum input - limit setpoint to
											// this
	private double m_minimumInput = 0.0; // minimum input - limit setpoint to
											// this
	private boolean m_continuous = false; // do the endpoints wrap around? eg.
											// Absolute encoder
	private double m_prevError = 0.0; // the prior sensor input (used to compute
										// velocity)
	private double m_totalError = 0.0; // the sum of the errors for use in the
										// integral calc
	private Supplier<Double> m_setpoint = () -> 0d;
	private double m_error = 0.0;
	private double m_result = 0.0;
	private double m_last_input = Double.NaN;
	private double m_deadband = 0.0; // If the absolute error is less than
										// deadband
										// then treat error for the proportional
										// term as 0

	/**
	 * Allocate a PID object with the given constants for P, I, D
	 * 
	 * @param Kp
	 *            the proportional coefficient
	 * @param Ki
	 *            the integral coefficient
	 * @param Kd
	 *            the derivative coefficient
	 */
	public SynchronusPID(double Kp, double Ki, double Kd, boolean readOnly) {
		m_P = Kp;
		m_I = Ki;
		m_D = Kd;
		m_readOnly = readOnly;
	}
	
	/**
	 * Read the input, calculate the output accordingly, and write to the
	 * output. This should be called at a constant rate by the user (ex. in a
	 * timed thread)
	 *
	 * @param input
	 *            the input
	 */
	public double calculate(double input) {
		m_last_input = input;
		m_error = m_setpoint.get() - input;
		if (m_continuous) {
			if (Math.abs(m_error) > (m_maximumInput - m_minimumInput) / 2) {
				if (m_error > 0) {
					m_error = m_error - m_maximumInput + m_minimumInput;
				} else {
					m_error = m_error + m_maximumInput - m_minimumInput;
				}
			}
		}

		if ((m_error * m_P < m_maximumOutput) && (m_error * m_P > m_minimumOutput)) {
			m_totalError += m_error;
		} else {
			m_totalError = 0;
		}

		// Don't blow away m_error so as to not break derivative
		double proportionalError = Math.abs(m_error) < m_deadband ? 0 : m_error;

		m_result = (m_P * proportionalError + m_I * m_totalError + m_D * (m_error - m_prevError));
		m_prevError = m_error;

		if (m_result > m_maximumOutput) {
			m_result = m_maximumOutput;
		} else if (m_result < m_minimumOutput) {
			m_result = m_minimumOutput;
		}
		return m_result;
	}
	
	public double calculateRate(double input) {
		if (m_P != 0) {
			double potentialPGain = (m_totalError + m_error) * m_P;
            if (potentialPGain < m_maximumOutput) {
              if (potentialPGain > m_minimumOutput) {
                m_totalError += m_error;
              } else {
                m_totalError = m_minimumOutput / m_P;
              }
            } else {
              m_totalError = m_maximumOutput / m_P;
            }

            m_result = m_P * m_totalError + m_D * m_error;
		} else {
			if (m_I != 0) {
	            double potentialIGain = (m_totalError + m_error) * m_I;
	            if (potentialIGain < m_maximumOutput) {
	              if (potentialIGain > m_minimumOutput) {
	                m_totalError += m_error;
	              } else {
	                m_totalError = m_minimumOutput / m_I;
	              }
	            } else {
	              m_totalError = m_maximumOutput / m_I;
	            }
	          }

	          m_result = m_P * m_error + m_I * m_totalError +
	                     m_D * (m_error - m_prevError);
		}
		m_prevError = m_error;

        if (m_result > m_maximumOutput) {
          m_result = m_maximumOutput;
        } else if (m_result < m_minimumOutput) {
          m_result = m_minimumOutput;
        }
        
        m_totalError += m_error;
        
        return m_result;
	}

	/**
	 * Set the PID controller gain parameters. Set the proportional, integral,
	 * and differential coefficients.
	 *
	 * @param p
	 *            Proportional coefficient
	 * @param i
	 *            Integral coefficient
	 * @param d
	 *            Differential coefficient
	 */
	public void setP(double p) {
		m_P = p;
	}
	
	public void setI(double i) {
		m_I = i;
	}
	
	public void setD(double d) { 
		m_D = d;
	}

	/**
	 * Get the Proportional coefficient
	 *
	 * @return proportional coefficient
	 */
	public double getP() {
		return m_P;
	}

	/**
	 * Get the Integral coefficient
	 *
	 * @return integral coefficient
	 */
	public double getI() {
		return m_I;
	}

	/**
	 * Get the Differential coefficient
	 *
	 * @return differential coefficient
	 */
	public double getD() {
		return m_D;
	}

	/**
	 * Return the current PID result This is always centered on zero and
	 * constrained the the max and min outs
	 *
	 * @return the latest calculated output
	 */
	public double get() {
		return m_result;
	}

	/**
	 * Set the PID controller to consider the input to be continuous, Rather
	 * then using the max and min in as constraints, it considers them to be the
	 * same point and automatically calculates the shortest route to the
	 * setpoint.
	 *
	 * @param continuous
	 *            Set to true turns on continuous, false turns off continuous
	 */
	public void setContinuous(boolean continuous) {
		m_continuous = continuous;
	}

	public void setDeadband(double deadband) {
		m_deadband = deadband;
	}

	/**
	 * Set the PID controller to consider the input to be continuous, Rather
	 * then using the max and min in as constraints, it considers them to be the
	 * same point and automatically calculates the shortest route to the
	 * setpoint.
	 */
	public void setContinuous() {
		this.setContinuous(true);
	}

	/**
	 * Sets the maximum and minimum values expected from the input.
	 *
	 * @param minimumInput
	 *            the minimum value expected from the input
	 * @param maximumInput
	 *            the maximum value expected from the output
	 */
	public void setInputRange(double minimumInput, double maximumInput) {
		if (minimumInput > maximumInput) {
			throw new BoundaryException("Lower bound is greater than upper bound");
		}
		m_minimumInput = minimumInput;
		m_maximumInput = maximumInput;
		setSetpoint(m_setpoint);
	}

	/**
	 * Sets the minimum and maximum values to write.
	 *
	 * @param minimumOutput
	 *            the minimum value to write to the output
	 * @param maximumOutput
	 *            the maximum value to write to the output
	 */
	public void setOutputRange(double minimumOutput, double maximumOutput) {
		if (minimumOutput > maximumOutput) {
			throw new BoundaryException("Lower bound is greater than upper bound");
		}
		m_minimumOutput = minimumOutput;
		m_maximumOutput = maximumOutput;
	}

	/**
	 * Set the setpoint for the PID controller
	 *
	 * @param setpoint
	 *            the desired setpoint
	 */
	public void setSetpoint(Supplier<Double> setpoint) {
		if (m_maximumInput > m_minimumInput) {
			if (setpoint.get() > m_maximumInput) {
				m_setpoint = () -> m_maximumInput;
			} else if (setpoint.get() < m_minimumInput) {
				m_setpoint = () -> m_minimumInput;
			} else {
				m_setpoint = setpoint;
			}
		} else {
			m_setpoint = setpoint;
		}
	}
	
	public void setSetpoint(double setpoint) {
		setSetpoint(() -> setpoint);
	}

	/**
	 * Returns the current setpoint of the PID controller
	 *
	 * @return the current setpoint
	 */
	public double getSetpoint() {
		return m_setpoint.get();
	}

	/**
	 * Returns the current difference of the input from the setpoint
	 *
	 * @return the current error
	 */
	public double getError() {
		return m_error;
	}

	/**
	 * Return true if the error is within the tolerance
	 *
	 * @return true if the error is less than the tolerance
	 */
	public boolean onTarget(double tolerance) {
		return m_last_input != Double.NaN && Math.abs(m_last_input - m_setpoint.get()) < tolerance;
	}

	/**
	 * Reset all internal terms.
	 */
	public void reset() {
		m_last_input = Double.NaN;
		m_prevError = 0;
		m_totalError = 0;
		m_result = 0;
//		m_setpoint = () -> 0d;
	}

	public void resetIntegrator() {
		m_totalError = 0;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("PIDController");
		builder.setSafeState(this::reset);
		if (!m_readOnly) {
		    builder.addDoubleProperty("p", this::getP, this::setP);
		    builder.addDoubleProperty("i", this::getI, this::setI);
		    builder.addDoubleProperty("d", this::getD, this::setD);
		    builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
		} else {
			builder.addDoubleProperty("p", this::getP, (x)->{});
		    builder.addDoubleProperty("i", this::getI, (x)->{});
		    builder.addDoubleProperty("d", this::getD, (x)->{});
		    builder.addDoubleProperty("setpoint", this::getSetpoint, (x)->{});
		}
	}
};