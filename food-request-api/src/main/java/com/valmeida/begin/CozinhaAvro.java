/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.valmeida.begin;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class CozinhaAvro extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -9023528093960020031L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CozinhaAvro\",\"namespace\":\"com.valmeida.begin\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"nome\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CozinhaAvro> ENCODER =
      new BinaryMessageEncoder<CozinhaAvro>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CozinhaAvro> DECODER =
      new BinaryMessageDecoder<CozinhaAvro>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<CozinhaAvro> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<CozinhaAvro> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CozinhaAvro>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this CozinhaAvro to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a CozinhaAvro from a ByteBuffer. */
  public static CozinhaAvro fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public long id;
  @Deprecated public java.lang.String nome;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CozinhaAvro() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param nome The new value for nome
   */
  public CozinhaAvro(java.lang.Long id, java.lang.String nome) {
    this.id = id;
    this.nome = nome;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return nome;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Long)value$; break;
    case 1: nome = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Long value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'nome' field.
   * @return The value of the 'nome' field.
   */
  public java.lang.String getNome() {
    return nome;
  }

  /**
   * Sets the value of the 'nome' field.
   * @param value the value to set.
   */
  public void setNome(java.lang.String value) {
    this.nome = value;
  }

  /**
   * Creates a new CozinhaAvro RecordBuilder.
   * @return A new CozinhaAvro RecordBuilder
   */
  public static com.valmeida.begin.CozinhaAvro.Builder newBuilder() {
    return new com.valmeida.begin.CozinhaAvro.Builder();
  }

  /**
   * Creates a new CozinhaAvro RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CozinhaAvro RecordBuilder
   */
  public static com.valmeida.begin.CozinhaAvro.Builder newBuilder(com.valmeida.begin.CozinhaAvro.Builder other) {
    return new com.valmeida.begin.CozinhaAvro.Builder(other);
  }

  /**
   * Creates a new CozinhaAvro RecordBuilder by copying an existing CozinhaAvro instance.
   * @param other The existing instance to copy.
   * @return A new CozinhaAvro RecordBuilder
   */
  public static com.valmeida.begin.CozinhaAvro.Builder newBuilder(com.valmeida.begin.CozinhaAvro other) {
    return new com.valmeida.begin.CozinhaAvro.Builder(other);
  }

  /**
   * RecordBuilder for CozinhaAvro instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CozinhaAvro>
    implements org.apache.avro.data.RecordBuilder<CozinhaAvro> {

    private long id;
    private java.lang.String nome;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.valmeida.begin.CozinhaAvro.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.nome)) {
        this.nome = data().deepCopy(fields()[1].schema(), other.nome);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing CozinhaAvro instance
     * @param other The existing instance to copy.
     */
    private Builder(com.valmeida.begin.CozinhaAvro other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.nome)) {
        this.nome = data().deepCopy(fields()[1].schema(), other.nome);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.Long getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.valmeida.begin.CozinhaAvro.Builder setId(long value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.valmeida.begin.CozinhaAvro.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'nome' field.
      * @return The value.
      */
    public java.lang.String getNome() {
      return nome;
    }

    /**
      * Sets the value of the 'nome' field.
      * @param value The value of 'nome'.
      * @return This builder.
      */
    public com.valmeida.begin.CozinhaAvro.Builder setNome(java.lang.String value) {
      validate(fields()[1], value);
      this.nome = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'nome' field has been set.
      * @return True if the 'nome' field has been set, false otherwise.
      */
    public boolean hasNome() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'nome' field.
      * @return This builder.
      */
    public com.valmeida.begin.CozinhaAvro.Builder clearNome() {
      nome = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CozinhaAvro build() {
      try {
        CozinhaAvro record = new CozinhaAvro();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
        record.nome = fieldSetFlags()[1] ? this.nome : (java.lang.String) defaultValue(fields()[1]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CozinhaAvro>
    WRITER$ = (org.apache.avro.io.DatumWriter<CozinhaAvro>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CozinhaAvro>
    READER$ = (org.apache.avro.io.DatumReader<CozinhaAvro>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}